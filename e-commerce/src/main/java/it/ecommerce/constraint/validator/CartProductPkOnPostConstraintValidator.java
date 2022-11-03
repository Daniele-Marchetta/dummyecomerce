package it.ecommerce.constraint.validator;

import java.lang.reflect.Field;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.CartProductPkOnPostConstraint;
import it.ecommerce.repository.CartProductRepository;

public class CartProductPkOnPostConstraintValidator
		implements ConstraintValidator<CartProductPkOnPostConstraint, Object> {

	@Autowired
	private CartProductRepository repo;

	private String cartFieldName;

	private String productFieldName;

	@Override
	public void initialize(CartProductPkOnPostConstraint constraintAnnotation) {
		cartFieldName = constraintAnnotation.cart();
		productFieldName = constraintAnnotation.product();
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		try {
			final Field cartDataField = value.getClass().getDeclaredField(cartFieldName);
			cartDataField.setAccessible(true);
			final Field productDataField = value.getClass().getDeclaredField(productFieldName);
			productDataField.setAccessible(true);

			final Integer cartId = (Integer) cartDataField.get(value);
			final Integer productId = (Integer) productDataField.get(value);

			if (repo.findByCartIdAndProductId(cartId, productId) == null) {
				return true;
			}
		} catch (Exception e) {
			System.out.print("exception");
		}
		return false;
	}

}
