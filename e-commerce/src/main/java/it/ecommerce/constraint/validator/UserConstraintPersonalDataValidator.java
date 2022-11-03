package it.ecommerce.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.UserConstraintPersonalData;
import it.ecommerce.repository.PersonalDataRepository;
import it.ecommerce.repository.UserRepository;

public class UserConstraintPersonalDataValidator implements ConstraintValidator<UserConstraintPersonalData, Integer> {
	@Autowired
	private UserRepository repoU;
	
	@Autowired
	private PersonalDataRepository repoP;

	@Override
	public void initialize(UserConstraintPersonalData constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if (!repoP.findById(value).isEmpty() && repoU.findByPersonalDataId(value)==null ) {
			return true;
		} else {
			return false;
		}
	}

}
