package it.ecommerce.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.UserConstraintRole;
import it.ecommerce.repository.RoleRepository;
import it.ecommerce.repository.UserRepository;

public class UserConstraintRoleValidator implements ConstraintValidator<UserConstraintRole, Integer> {
	@Autowired
	private RoleRepository repo;

	@Override
	public void initialize(UserConstraintRole constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		return !repo.findById(value).isEmpty();
	}

}
