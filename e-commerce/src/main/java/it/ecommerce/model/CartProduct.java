package it.ecommerce.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.ecommerce.model.compositeKey.CartProductCompositePK;

@Entity
@IdClass(CartProductCompositePK.class)
@Table(schema = "ec", name = "Cart_Products")
public class CartProduct {

	@Id
	@ManyToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;

	@Id
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "quantity")
	private Integer quantity;


	@Column(name = "price")
	private double price;

	@Column(name = "insertion_date")
	private LocalDateTime insertionDate;

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDateTime getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(LocalDateTime insertionDate) {
		this.insertionDate = insertionDate;
	}

	@Override
	public String toString() {
		return "CartProduct [cart=" + cart + ", product=" + product + ", quantity=" + quantity + ", price=" + price
				+ ", insertionDate=" + insertionDate + "]";
	}
	
}
