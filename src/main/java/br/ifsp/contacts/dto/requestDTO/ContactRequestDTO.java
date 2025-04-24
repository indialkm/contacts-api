package br.ifsp.contacts.dto.requestDTO;

import java.util.List;
import java.util.stream.Collectors;

import br.ifsp.contacts.model.Contact;


public class ContactRequestDTO {
	

	private String nome;
	private String telefone;
	private String email;	
	private List<AddressRequestDTO> addresses;
	
	
	public ContactRequestDTO(Contact contact) {
		
		this.nome = contact.getNome();
		this.telefone = contact.getTelefone();
		this.email = contact.getEmail();
		this.addresses = contact.getAddresses()
                .stream()
                .map(AddressRequestDTO::new) // 
                .collect(Collectors.toList());
	}
	

	public ContactRequestDTO() {
	}

	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getTelefone() {
		return telefone;
	}


	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public List<AddressRequestDTO> getAddresses() {
		return addresses;
	}


	public void setAddresses(List<AddressRequestDTO> addresses) {
		this.addresses = addresses;
	}
	
	
	

}
