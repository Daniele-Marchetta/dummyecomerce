package it.ecommerce.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.RoleConstraintId;
import it.ecommerce.constraint.RoleConstraintRole;
import it.ecommerce.repository.RoleRepository;

public class RoleConstraintIdValidator implements ConstraintValidator<RoleConstraintId, Integer> {
	@Autowired
	private RoleRepository repo;

	@Override
	public void initialize(RoleConstraintId constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) { 
		return !repo.findById(value).isEmpty();
	
	}

}
