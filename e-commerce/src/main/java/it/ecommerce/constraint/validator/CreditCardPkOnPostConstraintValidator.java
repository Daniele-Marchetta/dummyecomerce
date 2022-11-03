package it.ecommerce.constraint.validator;

import java.lang.reflect.Field;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.CreditCardPkOnPostConstraint;
import it.ecommerce.repository.CreditCardRepository;

public class CreditCardPkOnPostConstraintValidator implements ConstraintValidator<CreditCardPkOnPostConstraint, Object>{
	@Autowired
	private CreditCardRepository repo;

	private String userIdFieldName;

	private String creditCardNumFieldName;

	@Override
	public void initialize(CreditCardPkOnPostConstraint constraintAnnotation) {
		userIdFieldName = constraintAnnotation.userId();
		creditCardNumFieldName = constraintAnnotation.creditCardNum();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
			final Field userIdDataField = value.getClass().getDeclaredField(userIdFieldName);
			userIdDataField.setAccessible(true);
			final Field creditCardNumDataField = value.getClass().getDeclaredField(creditCardNumFieldName);
			creditCardNumDataField.setAccessible(true);

			final Integer userId = (Integer) userIdDataField.get(value);
			final String creditCardNum = (String) creditCardNumDataField.get(value);

			if (repo.findByUserIdAndCreditCardNum(userId, creditCardNum) == null) {
				return true;
			}
		} catch (Exception e) {
			System.out.print("exception");
		}
		return false;
	}

}
