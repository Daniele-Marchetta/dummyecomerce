package it.ecommerce.repository;

import javax.persistence.IdClass;

import org.springframework.data.repository.CrudRepository;

import it.ecommerce.model.OrderDetail;

public interface OrderDetailRepository extends CrudRepository<OrderDetail,IdClass > {

	public OrderDetail findByOrderIdAndProductId(Integer orderId, Integer productId);
	public void deleteByOrderIdAndProductId(Integer orderId, Integer productId);
}
