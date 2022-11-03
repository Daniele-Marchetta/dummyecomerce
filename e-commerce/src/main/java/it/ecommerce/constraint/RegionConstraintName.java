package it.ecommerce.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import it.ecommerce.constraint.validator.RegionConstraintNameValidator;

@Documented
@Target( { ElementType.METHOD, ElementType.FIELD })
@Constraint(validatedBy = RegionConstraintNameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegionConstraintName {
    String message() default "Region name already exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
