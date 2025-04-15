package br.ifsp.contacts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ifsp.contacts.model.Address;

public interface AddressRepository extends JpaRepository<Address,Long> {

}
