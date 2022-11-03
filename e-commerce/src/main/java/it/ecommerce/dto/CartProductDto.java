package it.ecommerce.dto;


import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

import it.ecommerce.constraint.CartProductCartIdConstraint;
import it.ecommerce.constraint.CartProductPkOnPostConstraint;
import it.ecommerce.constraint.CartProductProductIdConstraint;
import it.ecommerce.validation.group.OnCreate;


@CartProductPkOnPostConstraint(cart = "cartId", product = "productId", groups = OnCreate.class, message="This cart has already this product")
public class CartProductDto {
   
	@CartProductCartIdConstraint(message = "Invalid cart id")
	private Integer cartId;
	
	@CartProductProductIdConstraint(message = "Invalid product id")
	private Integer productId;
	
	@Null
	private String productName;

	@Min(value = 1, message = "Quantity cannot be null or negative")
	private Integer quantity;

	@Positive(message = "The price must be positive" )
	private double price;

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}




	
	

}
