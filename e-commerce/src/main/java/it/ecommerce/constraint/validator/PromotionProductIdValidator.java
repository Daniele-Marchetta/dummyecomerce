package it.ecommerce.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.PromotionConstraintProductId;
import it.ecommerce.repository.ProductRepository;

public class PromotionProductIdValidator implements ConstraintValidator<PromotionConstraintProductId, Integer>{
	@Autowired
	private ProductRepository repo;

	@Override
	public void initialize(PromotionConstraintProductId constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {	
     	return !repo.findById(value).isEmpty();
	}
	

}
