package it.ecommerce.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import it.ecommerce.constraint.validator.UserConstraintPersonalDataValidator;

@Documented
@Target( { ElementType.METHOD, ElementType.FIELD })
@Constraint(validatedBy = UserConstraintPersonalDataValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserConstraintPersonalData {
    String message() default "Wrong Personal Data id !";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}