package it.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import it.ecommerce.model.PersonalData;

public interface PersonalDataRepository extends CrudRepository <PersonalData, Integer>{
	
	

}
