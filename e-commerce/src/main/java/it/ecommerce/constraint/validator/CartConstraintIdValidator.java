package it.ecommerce.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.CartConstraintId;
import it.ecommerce.repository.CartRepository;

public class CartConstraintIdValidator implements ConstraintValidator<CartConstraintId, Integer>{
	@Autowired
	private CartRepository repo;

	@Override
	public void initialize(CartConstraintId constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {	
        if(value==null) {
        	return false;
        }
		return !repo.findById(value).isEmpty();
	}
	

}
