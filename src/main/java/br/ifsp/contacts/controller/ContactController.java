package br.ifsp.contacts.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.ifsp.contacts.dto.requestDTO.ContactRequestDTO;
import br.ifsp.contacts.dto.responseDTO.ContactResponseDTO;
import br.ifsp.contacts.exception.ResourceNotFoundException;
import br.ifsp.contacts.mapper.AddressMapper;
import br.ifsp.contacts.mapper.ContactMapper;
import br.ifsp.contacts.model.Contact;
import br.ifsp.contacts.repository.ContactRepository;
import jakarta.validation.Valid;

	@RestController
	@RequestMapping("/api/contacts")
	@Validated
	public class ContactController {

	    @Autowired
	    private ContactRepository contactRepository;
	    
	    @Autowired
	    private ContactMapper contactMapper;
	    
	    @Autowired
	    private AddressMapper addressMapper;
	   

	    @GetMapping
	    public Page<ContactResponseDTO> getAllContacts(Pageable pageable) {
	        Page<Contact> contactsPage = contactRepository.findAll(pageable);
	        return contactsPage.map(contactMapper::toResponse);
	    }

	    @GetMapping("{id}")
	    public ContactResponseDTO getContactById(@PathVariable Long id) {
	        
	    	Optional<Contact> contact = contactRepository.findById(id);
	    	 return contact.map(contactMapper::toResponse)
	                  .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + id));
	    }

	    @PostMapping
	    @ResponseStatus(HttpStatus.CREATED)
	    public ContactResponseDTO createContact(@Valid @RequestBody ContactRequestDTO contact) {
	        return contactMapper.toResponse(contactRepository.save(contactMapper.toEntity(contact)));
	    }

	    @PutMapping("/{id}")
	    public ContactResponseDTO updateContact(@PathVariable Long id, @Valid @RequestBody ContactRequestDTO updatedContact) {
	      
	    	Contact existingContact = contactRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + id));

	        existingContact.setNome(updatedContact.getNome());
	        existingContact.setEmail(updatedContact.getEmail());
	        existingContact.setTelefone(updatedContact.getTelefone());
	        existingContact.setAddresses(updatedContact.getAddresses().stream().map(address -> addressMapper.toEntity(address, existingContact)).collect(Collectors.toList()));
	        
	        contactRepository.save(existingContact);

	        return contactMapper.toResponse(existingContact);
	    }

	    @PatchMapping("/{id}")
	    public ContactResponseDTO updateContactPartial(@PathVariable Long id, @RequestBody Map<String, String> updates) {
	        Contact contact = contactRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + id));

	        updates.forEach((key, value) -> {
	            switch (key) {
	                case "nome":
	                    contact.setNome(value);
	                    break;
	                case "telefone":
	                    contact.setTelefone(value);
	                    break;
	                case "email":
	                    contact.setEmail(value);
	                    break;
	            }
	        });
	        
	        contactRepository.save(contact);

	        return contactMapper.toResponse(contact);
	    }

	    @DeleteMapping("/{id}")
	    public void deleteContact(@PathVariable Long id) {
	        contactRepository.deleteById(id);
	    }

	    @GetMapping("/search")
	    public List<ContactResponseDTO> searchContactsByName(@RequestParam String name) {
	        return contactRepository.findByNomeContainingIgnoreCase(name).stream().map(contactMapper::toResponse).collect(Collectors.toList());
	    }
	}


