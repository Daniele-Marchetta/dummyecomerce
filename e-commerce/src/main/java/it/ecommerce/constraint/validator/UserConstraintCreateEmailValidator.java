package it.ecommerce.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.UserConstraintCreateEmail;
import it.ecommerce.repository.UserRepository;

public class UserConstraintCreateEmailValidator implements ConstraintValidator<UserConstraintCreateEmail, String> {
	@Autowired
	private UserRepository repoU;

	@Override
	public void initialize(UserConstraintCreateEmail constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return repoU.findByEmail(value)==null;
	}

}
