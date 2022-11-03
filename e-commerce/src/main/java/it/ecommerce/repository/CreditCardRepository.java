package it.ecommerce.repository;

import javax.persistence.IdClass;

import org.springframework.data.repository.CrudRepository;

import it.ecommerce.model.CreditCard;

public interface CreditCardRepository extends CrudRepository<CreditCard, IdClass> {

	CreditCard findByUserIdAndCreditCardNum(Integer integer, String cardNum);

	void deleteByUserIdAndCreditCardNum(Integer userId, String cardNum);

	Iterable<CreditCard> findAllByUserId(Integer userId);
	
	CreditCard findByCreditCardNum(String creditCardNum);
}
