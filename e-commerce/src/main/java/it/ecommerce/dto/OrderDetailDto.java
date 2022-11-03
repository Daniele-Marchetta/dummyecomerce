package it.ecommerce.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import it.ecommerce.constraint.OrderConstraintId;
import it.ecommerce.constraint.OrderDetailPkOnPostConstraint;
import it.ecommerce.validation.group.OnCreate;
@OrderDetailPkOnPostConstraint(orderId = "orderId", productId = "productId", groups = OnCreate.class, message="This product is already present in this order")

public class OrderDetailDto {
	
	@OrderConstraintId(message="This order id doesn't exist")
	private Integer orderId;
	
	//@ProductConstraintId(message="This product id doesn't exist")//dovrebbe averlo fatto l'altro daniele
	private Integer productId;

	@NotNull(message = "Provide the current cost")
	@Positive(message = "The current cost cannot be negative")
	private double currentCost;

	@NotNull(message = "Provide the quantity")
	@Min(value = 1,message = "The quantity must be at least one")
	private Integer quantity;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public double getCurrentCost() {
		return currentCost;
	}

	public void setCurrentCost(double currentCost) {
		this.currentCost = currentCost;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
}
