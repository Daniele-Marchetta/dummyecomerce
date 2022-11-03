package it.ecommerce.constraint.validator;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.ProvinceConstraint;
import it.ecommerce.model.Region;
import it.ecommerce.repository.RegionRepository;

public class ProvinceConstraintValidator implements ConstraintValidator<ProvinceConstraint, Integer>{
	@Autowired
	private RegionRepository repo;

	@Override
	public void initialize(ProvinceConstraint constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
        Optional<Region> reg = repo.findById(value);
		return !(reg.isEmpty());
	}

}
