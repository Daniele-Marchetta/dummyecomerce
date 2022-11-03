package it.ecommerce.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.PromotionConstraintUserId;
import it.ecommerce.repository.UserRepository;

public class PromotionUserIdValidator implements ConstraintValidator<PromotionConstraintUserId, Integer> {
	@Autowired
	private UserRepository repo;

	@Override
	public void initialize(PromotionConstraintUserId constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {

		if (value == null) {
			return true;
		} else {
			return !repo.findById(value).isEmpty();
		}
	}

}
