package it.ecommerce.model.compositeKey;

import java.io.Serializable;
import java.util.Objects;

public class CartProductCompositePK implements Serializable {

	private Integer cart;
	
	private Integer product;

	public CartProductCompositePK() {
	}

	public CartProductCompositePK(Integer cart, Integer product) {
		super();
		this.cart = cart;
		this.product = product;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cart, product);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartProductCompositePK other = (CartProductCompositePK) obj;
		return Objects.equals(cart, other.cart) && Objects.equals(product, other.product);
	}

	
}
