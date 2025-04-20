package br.ifsp.contacts.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O nome não pode estar vazio")
	private String nome;
	
	@NotBlank(message = "O telefone não pode estar vazio")
    @Size(min = 8, max = 15, message = "O telefone deve ter entre 8 e 15 caracteres")
    @Pattern(regexp = "\\d+", message = "O telefone deve conter apenas números")
	private String telefone;

	@NotBlank(message = "O email não pode estar vazio")
    @Email(message = "Formato de email inválido")
	private String email;
	
	@OneToMany(mappedBy="contact", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
    @NotEmpty(message = "O contato deve ter pelo menos um endereço")
	private List<Address> addresses;
	
	
	public Contact() {
	}

	public Contact(Long id, @NotNull String nome, @Size(min = 8, max = 15) String telefone, @Email String email,
			List<Address> addresses) {
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.addresses = addresses;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
	    if (addresses != null) {
	        addresses.forEach(address -> address.setContact(this)); 
	        
	        if (this.addresses == null) { 
	            this.addresses = new ArrayList<>();
	        }
	        
	        this.addresses.clear(); 
	        this.addresses.addAll(addresses);         
	    }
	}


	

}
