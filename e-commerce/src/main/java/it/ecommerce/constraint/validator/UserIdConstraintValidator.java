package it.ecommerce.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.UserIdConstraint;
import it.ecommerce.repository.UserRepository;

public class UserIdConstraintValidator implements ConstraintValidator<UserIdConstraint, Integer> {

	@Autowired
	private UserRepository repo;
	
	@Override
	public void initialize(UserIdConstraint constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if(value==null)
			return false;
    return !repo.findById(value).isEmpty();
	}

}
