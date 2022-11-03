package it.ecommerce.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;


import it.ecommerce.constraint.UserConstraintCreateEmail;
import it.ecommerce.constraint.UserConstraintId;
import it.ecommerce.constraint.UserConstraintUpdate;
import it.ecommerce.constraint.UserConstraintPersonalData;
import it.ecommerce.constraint.UserConstraintRole;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;

@UserConstraintUpdate(UserId = "id" , mail = "email", pdata ="personalDataId",groups = OnUpdate.class)
public class UserDto {

	@Null(groups = OnCreate.class , message = "id must be null !")
	@NotNull(groups = OnUpdate.class , message = "Provide userId ")
	@UserConstraintId(groups = OnUpdate.class)
	private Integer id;

	@NotBlank(message = "Provide the password")
	private String hashedPassword;

	@NotBlank(message = "Provide the email")
	@Email
	@UserConstraintCreateEmail(groups = OnCreate.class)
	private String email;

	@NotNull(message = "Provide the personal data ID")
	@UserConstraintPersonalData(groups = OnCreate.class)
	private Integer personalDataId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPersonalDataId() {
		return personalDataId;
	}

	public void setPersonalDataId(Integer personalDataId) {
		this.personalDataId = personalDataId;
	}
}
