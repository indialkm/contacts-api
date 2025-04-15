package br.ifsp.contacts.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ifsp.contacts.model.Contact;
import br.ifsp.contacts.repository.ContactRepository;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
	
	@Autowired
	private ContactRepository contactRepository;
	
	
	@GetMapping("/{id}")
    public Contact getContactById(@PathVariable Long id) {
      
        return contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado: " + id));
    }
	
	@GetMapping("/nome/{nome}")
    public List<Contact> getContactNome(@PathVariable String nome) {
		
		 return contactRepository.findByNomeIgnoreCaseContaining(nome);
		 
    }
	
	@PostMapping
    public Contact createContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }
	
	@PatchMapping("/{id}")
	public Contact updateContactPartially(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
	    Contact contact = contactRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Contato não encontrado: " + id));

	    updates.forEach((key, value) -> {
	        switch (key) {
	            case "nome": contact.setNome((String) value); break;
	            case "email": contact.setEmail((String) value); break;
	            case "telefone": contact.setTelefone((String) value); break;
	        }
	    });

	    return contactRepository.save(contact);
	}

	
	@PutMapping("/{id}")
    public Contact updateContact(@PathVariable Long id, @RequestBody Contact updatedContact) {
        
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado: " + id));
     
        existingContact.setNome(updatedContact.getNome());
        existingContact.setTelefone(updatedContact.getTelefone());
        existingContact.setEmail(updatedContact.getEmail());

        return contactRepository.save(existingContact);
    }
	
	@DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Long id) {
        contactRepository.deleteById(id);
    }

}
