package it.ecommerce.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.RegionConstraintId;
import it.ecommerce.repository.RegionRepository;

public class RegionConstraintIdValidator implements ConstraintValidator<RegionConstraintId, Integer> {

	@Autowired
	private RegionRepository repo;
	
	@Override
	public void initialize(RegionConstraintId constraintAnnotation) {
	}


	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {

		if (value==null) {
			return false;
		} else {
			return !repo.findById(value).isEmpty();
		}
	}


}
