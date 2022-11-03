package it.ecommerce.dto;

import java.sql.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.ecommerce.constraint.CreditCardPkOnPostConstraint;
import it.ecommerce.constraint.UserIdConstraint;
import it.ecommerce.validation.group.OnCreate;
import lombok.NonNull;

@CreditCardPkOnPostConstraint(userId = "userId", creditCardNum = "creditCardNum", groups = OnCreate.class, message="This card has already been registered for the user")
public class CreditCardDto {

	@NonNull
	@UserIdConstraint(message = "Invalid user id")
	private Integer userId;

	@NotBlank(message = "Provide the credit card num")
	@Size(min = 16, max = 16, message = "The credit card num must be 16 chracters long")
	private String creditCardNum;

	@NotBlank(message = "Provide the name of the card holder")
	@Size(min = 1, max = 40, message = "The card holder must be long max 40 chracters")
	private String cardHolder;

	@Size(min = 3, max = 3, message = "The cvv must be 3 characters long")
	@NotBlank(message = "Provide the cvv")
	private String cvv;

	@Future(message = "The credit card expiration date is already passed")
	@NotNull(message = "Provide the expiration date")
	private Date expiration;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCreditCardNum() {
		return creditCardNum;
	}

	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
}
