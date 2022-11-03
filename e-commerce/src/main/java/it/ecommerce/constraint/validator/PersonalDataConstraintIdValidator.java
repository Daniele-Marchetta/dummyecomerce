package it.ecommerce.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.PersonalDataConstraintId;
import it.ecommerce.repository.PersonalDataRepository;

public class PersonalDataConstraintIdValidator implements ConstraintValidator<PersonalDataConstraintId, Integer>{
	@Autowired
	private PersonalDataRepository repo;

	@Override
	public void initialize(PersonalDataConstraintId constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {   
       return !repo.findById(value).isEmpty();
	}



}
