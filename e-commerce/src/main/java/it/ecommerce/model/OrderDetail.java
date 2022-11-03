package it.ecommerce.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import it.ecommerce.model.compositeKey.OrderDetailCompositeKey;

@Entity
@Table(schema = "ec", name = "Order_details")
@IdClass(OrderDetailCompositeKey.class)

public class OrderDetail {

	@Id
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "current_cost")
	private double currentCost;

	private Integer quantity;

	@Column(name = "insertion_date")
	private LocalDateTime insertionDate;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	public LocalDateTime getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(LocalDateTime insertionDate) {
		this.insertionDate = insertionDate;
	}

	@Override
	public String toString() {
		return "OrderDetail [order=" + order + ", product=" + product + ", currentCost=" + currentCost + ", quantity="
				+ quantity + ", insertionDate=" + insertionDate + "]";
	}

}
