package it.ecommerce.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import it.ecommerce.constraint.validator.UserConstraintUpdateValidator;

@Documented
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
@Constraint(validatedBy = UserConstraintUpdateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserConstraintUpdate {
    String message() default "Personal Data or Mail not valid !";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    String UserId();
    String mail();
    String pdata();
}