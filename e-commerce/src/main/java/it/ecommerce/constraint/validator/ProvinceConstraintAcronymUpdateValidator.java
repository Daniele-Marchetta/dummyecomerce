package it.ecommerce.constraint.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.ProvinceConstraintAcronymUpdate;
import it.ecommerce.repository.ProvinceRepository;

public class ProvinceConstraintAcronymUpdateValidator implements ConstraintValidator<ProvinceConstraintAcronymUpdate, String>{
	@Autowired
	private ProvinceRepository repo;

	@Override
	public void initialize(ProvinceConstraintAcronymUpdate constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
        return !repo.findById(value).isEmpty();
	}

}
