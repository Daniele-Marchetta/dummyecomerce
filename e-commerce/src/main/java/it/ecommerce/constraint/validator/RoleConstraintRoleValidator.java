package it.ecommerce.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.RoleConstraintRole;
import it.ecommerce.repository.RoleRepository;
import it.ecommerce.util.Util;

public class RoleConstraintRoleValidator implements ConstraintValidator<RoleConstraintRole, String>{
	@Autowired
	private RoleRepository repo;

	@Override
	public void initialize(RoleConstraintRole constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) { 
		if(repo.findByRole(Util.formatRole(value))==null) {
			return true;
		}else {
			return false;
		}
	}

}
