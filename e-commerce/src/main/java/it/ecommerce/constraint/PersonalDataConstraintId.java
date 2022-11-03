package it.ecommerce.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import it.ecommerce.constraint.validator.BrandConstraintIdValidator;
import it.ecommerce.constraint.validator.PersonalDataConstraintIdValidator;
import it.ecommerce.constraint.validator.ProductConstraintIdValidator;

@Documented
@Target( { ElementType.METHOD, ElementType.FIELD })
@Constraint(validatedBy = PersonalDataConstraintIdValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PersonalDataConstraintId {
    String message() default "This product id doesn't exist!!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}