package it.ecommerce.model.compositeKey;

import java.io.Serializable;
import java.util.Objects;


@SuppressWarnings("serial")
public class CreditCardCompositePK implements Serializable{
	
	private Integer user;
	
	private String creditCardNum;

	
	public Integer getUser() {
		return user;
	}

	public void setUser(Integer user) {
		this.user = user;
	}

	public String getCreditCardNum() {
		return creditCardNum;
	}

	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}

	@Override
	public int hashCode() {
		return Objects.hash(creditCardNum, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditCardCompositePK other = (CreditCardCompositePK) obj;
		return Objects.equals(creditCardNum, other.creditCardNum) && Objects.equals(user, other.user);
	}

	

	
}
