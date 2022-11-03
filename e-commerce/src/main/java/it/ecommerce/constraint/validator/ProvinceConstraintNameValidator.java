package it.ecommerce.constraint.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.ProvinceConstraintName;
import it.ecommerce.model.Province;
import it.ecommerce.repository.ProvinceRepository;

public class ProvinceConstraintNameValidator implements ConstraintValidator<ProvinceConstraintName, String>{
	@Autowired
	private ProvinceRepository repo;

	@Override
	public void initialize(ProvinceConstraintName constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
        Province p = repo.findByName(value);
        return  p == null;
	}

}
