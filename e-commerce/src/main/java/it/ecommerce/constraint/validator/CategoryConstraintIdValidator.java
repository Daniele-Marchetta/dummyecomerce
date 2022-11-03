package it.ecommerce.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.CategoryConstraintId;
import it.ecommerce.repository.CategoryRepository;

public class CategoryConstraintIdValidator implements ConstraintValidator<CategoryConstraintId, Integer> {

	@Autowired
	private CategoryRepository repo;
	
	@Override
	public void initialize(CategoryConstraintId constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if(value==null) {
        	return false;
        }
		return !repo.findById(value).isEmpty();
	}

}