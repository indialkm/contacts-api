package br.ifsp.contacts.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.ifsp.contacts.dto.requestDTO.AddressRequestDTO;
import br.ifsp.contacts.dto.responseDTO.AddressResponseDTO;
import br.ifsp.contacts.exception.ResourceNotFoundException;
import br.ifsp.contacts.mapper.AddressMapper;
import br.ifsp.contacts.model.Address;
import br.ifsp.contacts.model.Contact;
import br.ifsp.contacts.repository.AddressRepository;
import br.ifsp.contacts.repository.ContactRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
	@Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;
   
    @Autowired
    private AddressMapper addressMapper;
    
    @GetMapping("/contacts/{contactId}")
    public List<AddressResponseDTO> getAddressesByContact(@PathVariable Long contactId) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + contactId));
       
        return contact.getAddresses().stream()
                .map(address -> addressMapper.toResponse(address))  
                .collect(Collectors.toList());  
    }
    
    @PostMapping("/contacts/{contactId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponseDTO createAddress(@PathVariable Long contactId, @RequestBody @Valid AddressRequestDTO addressdto) {
    	Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + contactId));
        
    	Address address = addressMapper.toEntity(addressdto, contact);
        address.setContact(contact);
        
        Address saved = addressRepository.save(address);
        return addressMapper.toResponse(saved);
        
    }
    
    @PutMapping("/contacts/{contactId}/addresses/{addressId}")
    public AddressResponseDTO updateContact(@PathVariable Long contactId, @PathVariable Long addressId, @Valid @RequestBody AddressRequestDTO updatedAddressDTO) {
    	
    	Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + contactId));
    	
    	  Address address = contact.getAddresses().stream()
    			  	.filter(a-> a.getId().equals(addressId))
    			  	.findFirst()
    			  	.orElseThrow(()-> new ResourceNotFoundException("Endereço não encontrado" + addressId));
    	 
    	  	address.setRua(updatedAddressDTO.getRua());
    	    address.setCidade(updatedAddressDTO.getCidade());
    	    address.setEstado(updatedAddressDTO.getEstado());
    	    address.setCep(updatedAddressDTO.getCep());
    	    
    	    contactRepository.save(contact);
    	    
    	    return addressMapper.toResponse(address);

    }
    
    @PatchMapping("/contacts/{contactId}/addresses/{addressId}")
    public AddressResponseDTO updateContactPartial(@PathVariable Long contactId, @PathVariable Long addressId, @RequestBody Map<String, String> updates) {
		
    	Contact contact = contactRepository.findById(contactId)
    									   .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado" + addressId));
    	
    	Address address = contact.getAddresses().stream()
			  	.filter(a-> a.getId().equals(addressId))
			  	.findFirst()
			  	.orElseThrow(()-> new ResourceNotFoundException("Endereço não encontrado" + addressId));
    	
    	updates.forEach((key, value)-> {
            switch (key) {
            case "rua":
                address.setRua(value);
                break;
            case "cep":
            	 address.setCep(value);
                break;
            case "cidade":
                	address.setCidade(value);
                break;
            case "estado":
            		address.setEstado(value);
            		break;
        }
    });
    		addressRepository.save(address);

	        return addressMapper.toResponse(address);	
    	}
    	
    
    @DeleteMapping("/contacts/{contactId}/addresses/{addressId}")
    public void deleteAddress(@PathVariable Long contactId, @PathVariable Long addressId) {
    	  Contact contact = contactRepository.findById(contactId)
    	            .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado: " + contactId));

    	    Address address = contact.getAddresses().stream()
    	            .filter(a -> a.getId().equals(addressId))
    	            .findFirst()
    	            .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado: " + addressId));

    	    addressRepository.delete(address);
    }
    

}
