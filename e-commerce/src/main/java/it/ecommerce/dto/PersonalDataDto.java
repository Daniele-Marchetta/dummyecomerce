package it.ecommerce.dto;

import java.sql.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import it.ecommerce.constraint.PersonalDataConstraintId;
import it.ecommerce.constraint.ProvinceConstraintAcronymCreate;
import it.ecommerce.constraint.ProvinceConstraintAcronymUpdate;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;

public class PersonalDataDto {

	@NotNull(message = "id must be not null on PATCH", groups = OnUpdate.class)
	@Null(message = "id must be null on POST", groups = OnCreate.class )
	@PersonalDataConstraintId(groups = OnUpdate.class)
	private Integer id;

	@NotEmpty(message = "provide name")
	@Size(max = 20, message = "size of name must be < 20 char")
	private String name;

	@NotEmpty(message = "provide surname")
	@Size(max = 20, message = "size of surname must be < 20 char")
	private String surname;

	@Past(message = "date of birth must be in the past")
	private Date dateOfBirth;

	@Size(min = 10, max=10)
	@Pattern(regexp="^(0|[1-9][0-9]*)$" , message = "phone number must contain only numbers")
	private String phoneNumber;

	@NotEmpty(message = "provide city")
	@Size(max = 25)
	private String city;

	@NotEmpty(message = "provide cap")
	@Pattern(regexp="^(0|[1-9][0-9]*)$" , message = "cap must contain only numbers")
	@Size(max = 5, min = 5, message = "cap lenght must be equal to 5 char")
	private String cap;

	@NotEmpty(message = "provide province acronym")
	@Size(min = 2, max = 2, message = "province acronym must be equal to 2 char")
	@ProvinceConstraintAcronymUpdate
	//@ProvinceConstraintAcronymCreate(groups = OnCreate.class)
	private String provinceAcronym;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getProvinceAcronym() {
		return provinceAcronym;
	}

	public void setProvinceAcronym(String provinceAcronym) {
		this.provinceAcronym = provinceAcronym;
	}
}
