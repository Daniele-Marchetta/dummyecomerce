package it.ecommerce.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.ecommerce.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

	@Query("SELECT p FROM Product p JOIN Category c ON p.category=c.id WHERE c.name=?1")
	public Iterable<Product> getAllByCategoryName(String category);
}
