package it.ecommerce.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import it.ecommerce.constraint.validator.ProvinceConstraintAcronymUpdateValidator;

@Documented
@Target( { ElementType.METHOD, ElementType.FIELD })
@Constraint(validatedBy = ProvinceConstraintAcronymUpdateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProvinceConstraintAcronymUpdate {
    String message() default "Acronym not found !";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}