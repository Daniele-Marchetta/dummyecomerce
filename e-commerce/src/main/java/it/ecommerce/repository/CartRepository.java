package it.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import it.ecommerce.model.Cart;

public interface CartRepository extends CrudRepository<Cart, Integer>{
	public Cart findByUserId(Integer userid);
}
