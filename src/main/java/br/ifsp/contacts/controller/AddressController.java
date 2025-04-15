package br.ifsp.contacts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ifsp.contacts.model.Address;
import br.ifsp.contacts.repository.AddressRepository;

@RestController
@RequestMapping("/api/contact/{id}/addresses")
public class AddressController {
	
	@Autowired
	private AddressRepository addressRepository;
	
	@GetMapping
	public List<Address> getAddressByContactId(@PathVariable("id") Long contactId) {
		List<Address> addresses = addressRepository.findByContactId(contactId);
		
		if (addresses.isEmpty()) {
			throw new RuntimeException("Nenhum endereço encontrado para o contato com ID: " + contactId);
		}
		
		return addresses;
	}
	
	@PostMapping
	public Address insertAddress(@RequestBody Address address)
	{
		return addressRepository.save(address);
	}
	

}
