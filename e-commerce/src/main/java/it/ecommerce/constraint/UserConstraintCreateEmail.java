package it.ecommerce.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import it.ecommerce.constraint.validator.UserConstraintCreateEmailValidator;
import it.ecommerce.constraint.validator.UserConstraintPersonalDataValidator;

@Documented
@Target( { ElementType.METHOD, ElementType.FIELD })
@Constraint(validatedBy = UserConstraintCreateEmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserConstraintCreateEmail {
    String message() default "Email already exist !";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}