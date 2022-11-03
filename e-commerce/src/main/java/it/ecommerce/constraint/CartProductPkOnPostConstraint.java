package it.ecommerce.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import it.ecommerce.constraint.validator.CartProductPkOnPostConstraintValidator;


@Documented
@Target( {ElementType.METHOD,ElementType.FIELD, ElementType.TYPE})
@Constraint(validatedBy = CartProductPkOnPostConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface CartProductPkOnPostConstraint {
    String message() default "This cart has already this product";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String cart();
    String product();
}
