package it.ecommerce.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import it.ecommerce.constraint.validator.OrderConstraintIdValidator;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = OrderConstraintIdValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderConstraintId {
    String message() default "This order id doesn't exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
