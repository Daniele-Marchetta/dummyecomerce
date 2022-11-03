package it.ecommerce.repository;

import java.util.List;

import javax.persistence.IdClass;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import it.ecommerce.model.CartProduct;

public interface CartProductRepository extends CrudRepository<CartProduct, IdClass> {

	CartProduct findByCartIdAndProductId(Integer cartId, Integer productId);

	void deleteByCartIdAndProductId(Integer cartId, Integer productId);
	
	List<CartProduct> findByCartId(Integer cartId);
	
}
