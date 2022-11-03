package it.ecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.ecommerce.model.Province;

public interface ProvinceRepository extends CrudRepository<Province, String> {
public Province findByName(String name);
public List<Province> findByNameLike(String name);
}
