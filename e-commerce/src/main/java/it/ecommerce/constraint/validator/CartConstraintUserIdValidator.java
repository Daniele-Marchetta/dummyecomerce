package it.ecommerce.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.CartConstraintUserId;
import it.ecommerce.repository.CartRepository;
import it.ecommerce.repository.UserRepository;

public class CartConstraintUserIdValidator implements ConstraintValidator<CartConstraintUserId, Integer> {

	@Autowired
	private CartRepository repoC;

	@Autowired
	private UserRepository repoU;

	@Override
	public void initialize(CartConstraintUserId constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if (repoC.findByUserId(value) == null && !repoU.findById(value).isEmpty())
			return true;
		else
			return false;
	}

}
