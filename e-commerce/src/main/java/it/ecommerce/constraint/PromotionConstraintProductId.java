package it.ecommerce.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import it.ecommerce.constraint.validator.PromotionProductIdValidator;

@Documented
@Target( { ElementType.METHOD, ElementType.FIELD })
@Constraint(validatedBy = PromotionProductIdValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PromotionConstraintProductId {
    String message() default "Product Id must be valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
