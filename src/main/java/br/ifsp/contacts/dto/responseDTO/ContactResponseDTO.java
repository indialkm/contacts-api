package br.ifsp.contacts.dto.responseDTO;

import java.util.List;
import java.util.stream.Collectors;

import br.ifsp.contacts.model.Contact;


public class ContactResponseDTO {
	

	private String nome;
	private String telefone;
	private String email;	
	private List<AddressResponseDTO> addresses;
	
	
	public ContactResponseDTO(Contact contact) {
		this.nome = contact.getNome();
		this.telefone = contact.getTelefone();
		this.email = contact.getEmail();
		this.addresses = contact.getAddresses().stream()
											   .map(AddressResponseDTO::new)
											   .collect(Collectors.toList());
	}

	public ContactResponseDTO() {
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


	public List<AddressResponseDTO> getAddresses() {
		return addresses;
	}


	public void setAddresses(List<AddressResponseDTO> addresses) {
		this.addresses = addresses;
	}
	
	

}
