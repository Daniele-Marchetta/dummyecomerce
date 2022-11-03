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
import javax.persistence.Table;

@Entity
@Table(schema = "ec", name = "Orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
	private User user;

	@Column(name = "order_status")
	private Boolean orderStatus;

	@Column(name = "total_payment")
	private double totalPayment;
	
	@Column(name = "order_date")
	private LocalDateTime orderDate;

	@Column(name = "tracking_code")
	private String trackingCode;

	@Column(name = "insertion_date")
	private LocalDateTime insertionDate;

	@Column(name = "last_modified")
	private LocalDateTime lastModified;
	
	@OneToMany(mappedBy = "order")
	List<OrderDetail> orderDetails;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Boolean orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(double totalPayment) {
		this.totalPayment = totalPayment;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public String getTrackingCode() {
		return trackingCode;
	}

	public void setTrackingCode(String trackingCode) {
		this.trackingCode = trackingCode;
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

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", user=" + user + ", orderStatus=" + orderStatus + ", totalPayment=" + totalPayment
				+ ", orderDate=" + orderDate + ", trackingCode=" + trackingCode + ", insertionDate=" + insertionDate
				+ ", lastModified=" + lastModified + ", orderDetails=" + orderDetails + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, insertionDate, lastModified, orderDate, orderDetails, orderStatus, totalPayment,
				trackingCode, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(id, other.id) && Objects.equals(insertionDate, other.insertionDate)
				&& Objects.equals(lastModified, other.lastModified) && Objects.equals(orderDate, other.orderDate)
				&& Objects.equals(orderDetails, other.orderDetails) && Objects.equals(orderStatus, other.orderStatus)
				&& Double.doubleToLongBits(totalPayment) == Double.doubleToLongBits(other.totalPayment)
				&& Objects.equals(trackingCode, other.trackingCode) && Objects.equals(user, other.user);
	}
	
}
