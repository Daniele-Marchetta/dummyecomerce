package it.ecommerce.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.UserConstraintId;
import it.ecommerce.repository.UserRepository;

public class UserConstraintIdValidator implements ConstraintValidator<UserConstraintId, Integer>{
	@Autowired
	private UserRepository repo;

	@Override
	public void initialize(UserConstraintId constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {   
       return !repo.findById(value).isEmpty();
	}



}
