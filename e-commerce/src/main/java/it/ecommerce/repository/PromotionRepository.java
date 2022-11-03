package it.ecommerce.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.ecommerce.model.Product;
import it.ecommerce.model.Promotion;
import it.ecommerce.model.User;

public interface PromotionRepository extends CrudRepository<Promotion, Integer> {
	
    @Query("SELECT pr FROM Promotion pr WHERE pr.discountType=1 AND pr.product=?1 AND pr.user=?2 AND pr.endDate>=?3 AND pr.startDate<=?3")
	public Promotion getPersonalDiscount(Product product, User user, Date now);

	@Query("SELECT pr FROM Promotion pr WHERE pr.discountType=0 AND pr.product=?1 AND pr.endDate>=?2 AND pr.startDate<=?2")
	public Promotion getProductDiscount(Product product, Date now);

}
