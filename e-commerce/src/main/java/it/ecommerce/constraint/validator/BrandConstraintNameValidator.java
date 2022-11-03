package it.ecommerce.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.BrandConstraintName;
import it.ecommerce.model.Brand;
import it.ecommerce.repository.BrandRepository;

public class BrandConstraintNameValidator implements ConstraintValidator<BrandConstraintName, String>{
	@Autowired
	private BrandRepository repo;

	@Override
	public void initialize(BrandConstraintName constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {   
        Brand br = repo.findByName(value);
		return br==null;
	}



}
