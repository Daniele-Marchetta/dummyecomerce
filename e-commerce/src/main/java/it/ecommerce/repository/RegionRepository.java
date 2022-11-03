package it.ecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.ecommerce.model.Region;

public interface RegionRepository extends CrudRepository<Region, Integer> {

	public Region findByName(String nome);
	public List<Region> findByNameLike(String nome);
}
