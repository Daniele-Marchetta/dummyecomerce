package it.ecommerce.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import it.ecommerce.constraint.validator.ProvinceConstraintNameValidator;
import it.ecommerce.constraint.validator.RoleConstraintIdValidator;
import it.ecommerce.constraint.validator.RoleConstraintRoleValidator;

@Documented
@Target( { ElementType.METHOD, ElementType.FIELD })
@Constraint(validatedBy = RoleConstraintIdValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleConstraintId {
    String message() default "Role id doesn't exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}