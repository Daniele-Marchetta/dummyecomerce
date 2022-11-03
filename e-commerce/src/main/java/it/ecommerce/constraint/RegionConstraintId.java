package it.ecommerce.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import it.ecommerce.constraint.validator.RegionConstraintIdValidator;

@Documented
@Target( { ElementType.METHOD, ElementType.FIELD })
@Constraint(validatedBy = RegionConstraintIdValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegionConstraintId {
    String message() default "Region id is not valid !";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
