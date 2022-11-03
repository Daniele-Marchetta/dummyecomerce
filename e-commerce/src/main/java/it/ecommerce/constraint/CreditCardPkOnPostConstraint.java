package it.ecommerce.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import it.ecommerce.constraint.validator.CreditCardPkOnPostConstraintValidator;

@Documented
@Target( {ElementType.METHOD,ElementType.FIELD, ElementType.TYPE})
@Constraint(validatedBy = CreditCardPkOnPostConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreditCardPkOnPostConstraint {
    String message() default "This user has already registered thid credit card";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String userId();
    String creditCardNum();
}
