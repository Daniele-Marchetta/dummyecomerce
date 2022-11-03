package it.ecommerce.constraint.validator;

import java.lang.reflect.Field;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.OrderDetailPkOnPostConstraint;
import it.ecommerce.repository.OrderDetailRepository;

public class OrderDetailPkOnPostConstraintValidator implements ConstraintValidator<OrderDetailPkOnPostConstraint ,Object> {

	@Autowired
	private OrderDetailRepository repo;

	private String orderIdFieldName;

	private String productIdFieldName;
	
	
	@Override
	public void initialize(OrderDetailPkOnPostConstraint constraintAnnotation) {
		orderIdFieldName = constraintAnnotation.orderId();
		productIdFieldName = constraintAnnotation.productId();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
			final Field orderIdDataField = value.getClass().getDeclaredField(orderIdFieldName);
			orderIdDataField.setAccessible(true);
			final Field productIdDataField = value.getClass().getDeclaredField(productIdFieldName);
			productIdDataField.setAccessible(true);
			final Integer orderId = (Integer) orderIdDataField.get(value);
			final Integer productId = (Integer) productIdDataField.get(value);
			if (repo.findByOrderIdAndProductId(orderId, productId) == null) {
				return true;
			}
		} catch (Exception e) {
			System.out.print("exception");
		}
		return false;
	}

}
