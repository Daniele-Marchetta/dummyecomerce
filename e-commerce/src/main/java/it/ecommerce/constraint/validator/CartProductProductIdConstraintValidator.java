package it.ecommerce.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.CartProductCartIdConstraint;
import it.ecommerce.constraint.CartProductProductIdConstraint;
import it.ecommerce.repository.ProductRepository;

public class CartProductProductIdConstraintValidator implements ConstraintValidator<CartProductProductIdConstraint, Integer>{
	@Autowired
	private ProductRepository repo;

	@Override
	public void initialize(CartProductProductIdConstraint constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if(value==null)
			return false;
		return !repo.findById(value).isEmpty();
	}
}
