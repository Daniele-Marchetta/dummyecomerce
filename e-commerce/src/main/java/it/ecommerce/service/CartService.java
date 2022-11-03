package it.ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.ecommerce.dto.CartDto;
import it.ecommerce.exceptions.DatabaseException;
import it.ecommerce.exceptions.ResourceNotFoundException;
import it.ecommerce.model.Cart;
import it.ecommerce.model.CartProduct;
import it.ecommerce.repository.CartProductRepository;
import it.ecommerce.repository.CartRepository;

@Service
@Transactional
public class CartService {

	@Autowired
	private CartRepository repo;

	@Autowired
	private ModelMapper modelMapper;

	public Cart getById(Integer id) {
		return repo.findById(id).get();
	}

	public Iterable<Cart> getAll() {
		return repo.findAll();
	}

	public Cart insert(Cart c) {
		c.setInsertionDate(LocalDateTime.now());

		if (repo.findByUserId(c.getUserId()) != null) {
			throw new DatabaseException("Cart alredy exist for this user");
		}
		return repo.save(c);
	}

	public Cart update(Cart c) {
		Cart backup = new Cart();
		backup = repo.findById(c.getId()).get();
		c.setInsertionDate(backup.getInsertionDate());

		if (repo.findById(c.getId()) == null) {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
		return repo.save(c);
	}

	public void delete(Cart c) {

		Optional<Cart> cart = repo.findById(c.getId());

		if (!cart.isEmpty()) {
			repo.delete(c);
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}

	}
	
	public Iterable<CartDto> convertEntityToDto(Iterable<Cart> listc) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		CartDto cartDto = new CartDto();
		List<CartDto> listDto = new ArrayList<CartDto>();
		for (Cart c : listc) {
			cartDto = modelMapper.map(c, CartDto.class);
			listDto.add(cartDto);
		}
		return listDto;
	}

	/**
	 * converte l'entità cart nell'entità cartDto ritorna un oggettto di tipo
	 * cartdto
	 * 
	 * @param c
	 * @return
	 */
	public CartDto convertEntityToDto(Cart c) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		CartDto CartDto = new CartDto();
		CartDto = modelMapper.map(c, CartDto.class);
		return CartDto;
	}

	/**
	 * converte l'oggetto di tipo cartDto in cart ritorna un oggetto di tipo cart
	 * 
	 * @param c
	 * @return
	 */
	public Cart convertDtoToEntity(CartDto c) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		Cart cat = new Cart();
		cat = modelMapper.map(c, Cart.class);
		return cat;

	}

	/**
	 * converte l'oggetto di tipo cartdto in un oggetto di tipo cart restituisce
	 * l'insert della cart
	 * 
	 * @param cat
	 * @return
	 */
	public Cart insertDto(CartDto cat) {
		Cart cart = convertDtoToEntity(cat);
		cart.setInsertionDate(LocalDateTime.now());
		return repo.save(cart);
	}

	/**
	 * converte un oggetto di tipo cartDto in un'altro di tipo cart restituisce
	 * l'update sul db
	 * 
	 * @param cat
	 * @return
	 */
	public Cart updateDto(CartDto cat) {

		Cart cart = convertDtoToEntity(cat);
		Cart backup = new Cart();

		backup = repo.findById(cart.getId()).get();
		cart.setInsertionDate(backup.getInsertionDate());

		return repo.save(cart);
	}
	
}
