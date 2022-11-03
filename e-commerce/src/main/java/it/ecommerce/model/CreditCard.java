package it.ecommerce.model;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//import com.fasterxml.jackson.annotation.JsonBackReference;

import it.ecommerce.model.compositeKey.CreditCardCompositePK;

@Entity
@IdClass(CreditCardCompositePK.class)
@Table(schema = "ec", name = "Credit_cards")
public class CreditCard {

	@Id
	@JoinColumn(name = "user_id")
	@ManyToOne
//	@JsonBackReference
	private User user;
	@Id
	@Column(name = "credit_card_num")
	private String creditCardNum;

	@Column(name = "card_holder")
	private String cardHolder;

	private String cvv;

	private Date expiration;

	@Column(name = "insertion_date")
	private LocalDateTime insertionDate;

	@Column(name = "last_modified")
	private LocalDateTime lastModified;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public LocalDateTime getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(LocalDateTime insertionDate) {
		this.insertionDate = insertionDate;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public String toString() {
		return "CreditCard [user=" + user + ", creditCardNum=" + creditCardNum + ", cardHolder=" + cardHolder + ", cvv="
				+ cvv + ", expiration=" + expiration + ", insertionDate=" + insertionDate + ", lastModified="
				+ lastModified + "]";
	}

	

}
