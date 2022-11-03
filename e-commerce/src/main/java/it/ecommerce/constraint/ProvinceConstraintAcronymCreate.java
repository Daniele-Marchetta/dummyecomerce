package it.ecommerce.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import it.ecommerce.constraint.validator.ProvinceConstraintAcronymCreateValidator;

@Documented
@Target( { ElementType.METHOD, ElementType.FIELD })
@Constraint(validatedBy = ProvinceConstraintAcronymCreateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProvinceConstraintAcronymCreate {
    String message() default "This acronym already exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}