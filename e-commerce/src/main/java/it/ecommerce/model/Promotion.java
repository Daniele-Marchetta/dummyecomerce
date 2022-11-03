package it.ecommerce.model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//import org.hibernate.annotations.LazyGroup;
//import org.hibernate.annotations.LazyToOne;
//import org.hibernate.annotations.LazyToOneOption;

@Entity
@Table(schema = "ec", name = "Promotions")
public class Promotion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer discount;

	@Column(name = "discount_type")
	private Boolean discountType;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@ManyToOne(optional = true)
//	@JsonManagedReference
	@JoinColumn(name = "user_id",nullable = true)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
//	@JsonBackReference
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "insertion_date")
	private LocalDateTime insertionDate;

	@Column(name = "last_modified")
	private LocalDateTime lastModified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Boolean getDiscountType() {
		return discountType;
	}

	public void setDiscountType(Boolean discountType) {
		this.discountType = discountType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
//	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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
		return "Promotion [id=" + id + ", discount=" + discount + ", discountType=" + discountType + ", startDate="
				+ startDate + ", endDate=" + endDate + ", user=" + user + ", product=" + product + ", insertionDate="
				+ insertionDate + ", lastModified=" + lastModified + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(discount, discountType, endDate, id, insertionDate, lastModified, product, startDate, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Promotion other = (Promotion) obj;
		return Objects.equals(discount, other.discount) && Objects.equals(discountType, other.discountType)
				&& Objects.equals(endDate, other.endDate) && Objects.equals(id, other.id)
				&& Objects.equals(insertionDate, other.insertionDate)
				&& Objects.equals(lastModified, other.lastModified) && Objects.equals(product, other.product)
				&& Objects.equals(startDate, other.startDate) && Objects.equals(user, other.user);
	}



}
