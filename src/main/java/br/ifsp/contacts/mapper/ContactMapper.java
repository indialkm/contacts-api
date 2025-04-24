package br.ifsp.contacts.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ifsp.contacts.dto.requestDTO.ContactRequestDTO;
import br.ifsp.contacts.dto.responseDTO.ContactResponseDTO;
import br.ifsp.contacts.model.Contact;

@Component
public class ContactMapper {
	
	@Autowired
	private AddressMapper addressMapper;
	
	public Contact toEntity(ContactRequestDTO dto) {
        Contact contact = new Contact();
        contact.setNome(dto.getNome());
        contact.setEmail(dto.getEmail());
        contact.setTelefone(dto.getTelefone());
        
        
        contact.setAddresses(dto.getAddresses().stream()
                .map(address -> addressMapper.toEntity(address, contact))
                .collect(Collectors.toList()));

        return contact;
    }
	
	public ContactResponseDTO toResponse(Contact contact) {
        return new ContactResponseDTO(contact);
    }
	
	public ContactRequestDTO toRequest(Contact contact) {
        return new ContactRequestDTO(contact);
    }

}
