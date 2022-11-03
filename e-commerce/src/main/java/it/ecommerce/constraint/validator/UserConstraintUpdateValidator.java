package it.ecommerce.constraint.validator;

import java.lang.reflect.Field;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.ecommerce.constraint.UserConstraintUpdate;
import it.ecommerce.model.PersonalData;
import it.ecommerce.model.User;
import it.ecommerce.repository.PersonalDataRepository;
import it.ecommerce.repository.UserRepository;

public class UserConstraintUpdateValidator implements ConstraintValidator<UserConstraintUpdate, Object> {
	@Autowired
	private UserRepository repoU;
	@Autowired
	private PersonalDataRepository repoP;

	private String UserIdFieldName;
	private String MailFieldName;
	private String PdataFieldName;

	@Override
	public void initialize(UserConstraintUpdate constraintAnnotation) {
		UserIdFieldName = constraintAnnotation.UserId();
		MailFieldName = constraintAnnotation.mail();
		PdataFieldName = constraintAnnotation.pdata();
	}

	@Override
	public boolean isValid(final Object value, ConstraintValidatorContext context) {
		try {

			final Field userDataField = value.getClass().getDeclaredField(UserIdFieldName);
			userDataField.setAccessible(true);

			final Field mailDataField = value.getClass().getDeclaredField(MailFieldName);
			mailDataField.setAccessible(true);

			final Field pdataField = value.getClass().getDeclaredField(PdataFieldName);
			pdataField.setAccessible(true);

			final Integer userId = (Integer) userDataField.get(value);
			final String email = (String) mailDataField.get(value);
			final Integer pdataId = (Integer) pdataField.get(value);

			Boolean Mailcheck;
			Boolean Pdatacheck;

			// controllo mail

			User backup = repoU.findByEmail(email);

			if (backup != null) {
				if (backup.getEmail().equals(email) && backup.getId() == userId) {
					// mail valida
					Mailcheck = true;

				} else {
					Mailcheck = false;
				}
			} else {
				Mailcheck = true;
			}

			// check personal data
			Optional<PersonalData> backupPdata = repoP.findById(pdataId);

			if (backupPdata.isEmpty()) {

				Pdatacheck = false;

			} else {
				if (pdataId == backupPdata.get().getId()) {
					User userpersonalcheck = repoU.findByPersonalDataId(pdataId);
					if (userpersonalcheck != null) {
						if (userpersonalcheck.getId() != pdataId) {
							return true;
						} else {
							Pdatacheck = false;

						}

					} else {
						Pdatacheck = true;

					}
				} else {
					Pdatacheck = false;
				}
			}

			if (Mailcheck == true && Pdatacheck == true) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			System.out.print("exception");
		}
		return false;

	}

}
