package it.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import it.ecommerce.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	public Iterable<User> findAllByRoleId(Integer roleId);

	public User findByEmail(String email);
	
	public User findByPersonalDataId(Integer id);
}
