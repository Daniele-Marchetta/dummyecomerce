package it.ecommerce.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.ecommerce.model.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
	public Order findByTrackingCode(String track);
	
	@Query("select u.orderStatus from Order u where u.id is ?1")
	 public Boolean checkDelete(Integer id);
	
	
	@Query("select u from Order u join User us on us.id = u.user where us.id is ?1")
	public Iterable<Order> findAllbyUserId(Integer id);
	
}
