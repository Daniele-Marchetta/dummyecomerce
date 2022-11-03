package it.ecommerce.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(schema = "ec", name = "Users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "hashed_password")
	private String hashedPassword;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	@Column(name = "email")
	private String email;

	@Column(name = "last_modified")
	private LocalDateTime lastModified;

	@Column(name = "insertion_date")
	private LocalDateTime insertionDate;

	@OneToOne
	@JoinColumn(name = "personal_data_id")
	private PersonalData personalData;

	@OneToMany(mappedBy = "user")
//	@JsonManagedReference
	private List<CreditCard> cards;
	
	@OneToMany(mappedBy = "user")
	private List<Order> orders;
	
	@OneToMany(mappedBy = "user")
//	@JsonBackReference
    private List<Promotion> promotions;

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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	public LocalDateTime getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(LocalDateTime insertionDate) {
		this.insertionDate = insertionDate;
	}

	public PersonalData getPersonalData() {
		return personalData;
	}

	public void setPersonalData(PersonalData personalData) {
		this.personalData = personalData;
	}

	public List<CreditCard> getCards() {
		return cards;
	}

	public void setCards(List<CreditCard> cards) {
		this.cards = cards;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<Promotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(List<Promotion> promotions) {
		this.promotions = promotions;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", hashedPassword=" + hashedPassword + ", role=" + role + ", email=" + email
				+ ", lastModified=" + lastModified + ", insertionDate=" + insertionDate + ", personalData="
				+ personalData + ", cards=" + cards + ", orders=" + orders + ", promotions=" + promotions + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cards, email, hashedPassword, id, insertionDate, lastModified, orders, personalData,
				promotions, role);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(cards, other.cards) && Objects.equals(email, other.email)
				&& Objects.equals(hashedPassword, other.hashedPassword) && Objects.equals(id, other.id)
				&& Objects.equals(insertionDate, other.insertionDate)
				&& Objects.equals(lastModified, other.lastModified) && Objects.equals(orders, other.orders)
				&& Objects.equals(personalData, other.personalData) && Objects.equals(promotions, other.promotions)
				&& Objects.equals(role, other.role);
	}

	

	

	
	
}