package it.ecommerce.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import it.ecommerce.constraint.OrderConstraintId;
import it.ecommerce.constraint.UserIdConstraint;
import it.ecommerce.validation.group.OnUpdate;

public class OrderDto {

	@OrderConstraintId(message="This order id doesn't exist", groups = OnUpdate.class)
	private Integer id;

	@UserIdConstraint(message="This user id doesn't exist")
	@NotNull(message = "Provide the user ID")
	private Integer userId;

	@NotNull(message = "Provide the order status")
	private Boolean orderStatus;

	@NotNull(message = "Provide the total payment")
	@Positive(message="The payment must be positive")
	private double totalPayment;
	
	@Size(min = 20, max = 20, message = "The tracking code must be 20 characters long")
	@NotNull(message="Provide the tracking code")
	private String trackingCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getTrackingCode() {
		return trackingCode;
	}

	public void setTrackingCode(String trackingCode) {
		this.trackingCode = trackingCode;
	}

}
