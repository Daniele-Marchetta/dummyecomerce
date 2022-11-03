package it.ecommerce.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import it.ecommerce.constraint.validator.BrandConstraintNameValidator;

@Documented
@Target( { ElementType.METHOD, ElementType.FIELD })
@Constraint(validatedBy = BrandConstraintNameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface BrandConstraintName {
    String message() default "Already existing name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
