package it.ecommerce.constraint.validator;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.ProvinceConstraintAcronymCreate;
import it.ecommerce.repository.ProvinceRepository;

public class ProvinceConstraintAcronymCreateValidator implements ConstraintValidator<ProvinceConstraintAcronymCreate, String>{
	@Autowired
	private ProvinceRepository repo;

	@Override
	public void initialize(ProvinceConstraintAcronymCreate constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
        return repo.findById(value)==null;
	}

}
