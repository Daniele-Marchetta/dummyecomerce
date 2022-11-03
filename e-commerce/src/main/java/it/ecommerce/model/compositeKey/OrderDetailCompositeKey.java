package it.ecommerce.model.compositeKey;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class OrderDetailCompositeKey implements Serializable{
	
	private Integer order;
	private Integer product;
	
	public OrderDetailCompositeKey() {
		
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getProduct() {
		return product;
	}

	public void setProduct(Integer product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		return Objects.hash(order, product);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDetailCompositeKey other = (OrderDetailCompositeKey) obj;
		return Objects.equals(order, other.order) && Objects.equals(product, other.product);
	}

	public OrderDetailCompositeKey(Integer order, Integer product) {
		super();
		this.order = order;
		this.product = product;
	}
	
}
