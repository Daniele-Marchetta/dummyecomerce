package it.ecommerce.constraint.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.RegionConstraintName;
import it.ecommerce.model.Region;
import it.ecommerce.repository.RegionRepository;

public class RegionConstraintNameValidator implements ConstraintValidator<RegionConstraintName, String>{
	@Autowired
	private RegionRepository repo;

	@Override
	public void initialize(RegionConstraintName constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Region r = repo.findByName(value);
		return r==null;
	}




}
