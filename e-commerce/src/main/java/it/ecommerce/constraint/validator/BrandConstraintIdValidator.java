package it.ecommerce.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.BrandConstraintId;
import it.ecommerce.repository.BrandRepository;

public class BrandConstraintIdValidator implements ConstraintValidator<BrandConstraintId, Integer>{
	@Autowired
	private BrandRepository repo;

	@Override
	public void initialize(BrandConstraintId constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) { 
        if(value==null) {
        	return false;
        }
		return !repo.findById(value).isEmpty();
	}

}
