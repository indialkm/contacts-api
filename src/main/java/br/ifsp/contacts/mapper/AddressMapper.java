package br.ifsp.contacts.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ifsp.contacts.dto.requestDTO.AddressRequestDTO;
import br.ifsp.contacts.dto.responseDTO.AddressResponseDTO;
import br.ifsp.contacts.model.Address;
import br.ifsp.contacts.model.Contact;

@Component
public class AddressMapper {
	

		    public Address toEntity(AddressRequestDTO dto, Contact contact) {
		        Address address = new Address();
		        address.setRua(dto.getRua());
		        address.setCidade(dto.getCidade());
		        address.setEstado(dto.getEstado());
		        address.setCep(dto.getCep());
		        address.setContact(contact); 
		        return address;
		    }

		    public AddressResponseDTO toResponse(Address address) {
		        return new AddressResponseDTO(address);
		    }
		    
		    public AddressRequestDTO toRequest(Address address) {
		        return new AddressRequestDTO(address);
		    }

}
