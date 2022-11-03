package it.ecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.ecommerce.model.Brand;

public interface BrandRepository extends CrudRepository<Brand, Integer> {

	public Brand findByName(String name);
	public List<Brand> findAllByNameLike(String name);
}
