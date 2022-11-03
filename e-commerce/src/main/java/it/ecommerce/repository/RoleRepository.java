package it.ecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.ecommerce.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
	public Role findByRole(String name);
	public List<Role> findByRoleLike(String name);
}
