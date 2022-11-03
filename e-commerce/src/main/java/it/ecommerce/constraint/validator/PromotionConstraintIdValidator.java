package it.ecommerce.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.PromotionConstraintId;
import it.ecommerce.repository.PromotionRepository;

public class PromotionConstraintIdValidator implements ConstraintValidator<PromotionConstraintId, Integer>{
	@Autowired
	private PromotionRepository repo;

	@Override
	public void initialize(PromotionConstraintId constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {   
       return !repo.findById(value).isEmpty();
	}



}
