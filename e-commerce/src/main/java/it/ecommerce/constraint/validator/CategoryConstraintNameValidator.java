package it.ecommerce.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.CategoryConstraintName;
import it.ecommerce.model.Category;
import it.ecommerce.repository.CategoryRepository;

public class CategoryConstraintNameValidator implements ConstraintValidator<CategoryConstraintName, String>{
	@Autowired
	private CategoryRepository repo;

	@Override
	public void initialize(CategoryConstraintName constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
        Category cat = repo.findByname(value);
		return cat==null;
	}




}
