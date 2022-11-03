package it.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import it.ecommerce.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
	
	public Iterable<Category> findBynameLike(String name);
	
	public Category findByname(String name);
}
