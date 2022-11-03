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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import it.ecommerce.dto.CartProductDto;
import it.ecommerce.exceptions.DatabaseException;
import it.ecommerce.exceptions.NoDataFoundException;
import it.ecommerce.exceptions.ResourceNotFoundException;
import it.ecommerce.model.Cart;
import it.ecommerce.model.CartProduct;
import it.ecommerce.model.User;
import it.ecommerce.repository.CartProductRepository;
import it.ecommerce.repository.CartRepository;
import it.ecommerce.repository.ProductRepository;
import it.ecommerce.repository.UserRepository;
import it.ecommerce.security.SecurityUser;
import it.ecommerce.util.Util;

@Service
@Transactional
public class CartProductService {

	@Autowired
	private CartProductRepository repo;

	@Autowired
	ProductRepository repoProd;

	@Autowired
	CartRepository repoCart;
	
	@Autowired
	UserRepository repoUser;

	@Autowired
	private ModelMapper modelMapper;

	public CartProduct getById(Integer cartId, Integer productId) {
		CartProduct c = new CartProduct();
		c = repo.findByCartIdAndProductId(cartId, productId);
		if (c != null) {
			return c;
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	public Iterable<CartProduct> getAll() {

		Iterable<CartProduct> iter = repo.findAll();

		if (iter.iterator().hasNext()) {
			return iter;
		} else {
			throw new NoDataFoundException();
		}
	}

	public CartProduct insert(CartProduct c) {
		c.setInsertionDate(LocalDateTime.now());
		if (repo.findByCartIdAndProductId(c.getCart().getId(), c.getProduct().getId()) != null) {
			throw new DatabaseException("Composite key already exist !");
		} else {
			return repo.save(c);
		}

	}
	
	public CartProduct insert(CartProduct c, Authentication auth) {
		c.setInsertionDate(LocalDateTime.now());
		if (repo.findByCartIdAndProductId(c.getCart().getId(), c.getProduct().getId()) != null) {
			throw new DatabaseException("Composite key already exist !");
		} else {
			 String customerMail = (String) auth.getPrincipal();
			 Cart cart = repoCart.findByUserId(repoUser.findByEmail(customerMail).getId());
			 c.setCart(cart);
			return repo.save(c);
		}

	}

	public CartProduct update(CartProduct c) {
		CartProduct backup = new CartProduct();
		backup = repo.findByCartIdAndProductId(c.getCart().getId(), c.getProduct().getId());
		c.setInsertionDate(backup.getInsertionDate());
		if (repo.findByCartIdAndProductId(c.getCart().getId(), c.getProduct().getId()) == null) {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		} else {
			return repo.save(c);
		}

	}
	

	public CartProduct update(CartProduct c, Authentication auth) {
		CartProduct backup = new CartProduct();
		backup = repo.findByCartIdAndProductId(c.getCart().getId(), c.getProduct().getId());
		c.setInsertionDate(backup.getInsertionDate());
		if (repo.findByCartIdAndProductId(c.getCart().getId(), c.getProduct().getId()) == null) {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		} else {
			String CustomerMail = (String) auth.getPrincipal();
			 Cart cart = repoCart.findByUserId(repoUser.findByEmail(CustomerMail).getId());
			 c.setCart(cart);
			return repo.save(c);
		}

	}

	public void delete(CartProduct c) {
		CartProduct cprod = repo.findByCartIdAndProductId(c.getCart().getId(), c.getProduct().getId());

		if (cprod != null) {
			repo.deleteByCartIdAndProductId(c.getCart().getId(), c.getProduct().getId());
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	/**
	 * converte l'entità categoria nell'entità categoriaDto ritorna un oggettto di
	 * tipo cartproductdto
	 * 
	 * @param c
	 * @return
	 */
	public CartProductDto convertEntityToDto(CartProduct c) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		CartProductDto CartProductDto = new CartProductDto();
		CartProductDto = modelMapper.map(c, CartProductDto.class);
		return CartProductDto;
	}

	public Iterable<CartProductDto> convertEntityToDto(Iterable<CartProduct> listc) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		CartProductDto cartProductDto = new CartProductDto();
		List<CartProductDto> listDto = new ArrayList<CartProductDto>();
		for (CartProduct c : listc) {
			cartProductDto = modelMapper.map(c, CartProductDto.class);
			listDto.add(cartProductDto);
		}
		return listDto;
	}

	/**
	 * converte l'oggetto di tipo categoriaDto in categoria ritorna un oggetto di
	 * tipo cartproduct
	 * 
	 * @param c
	 * @return
	 */
	public CartProduct convertDtoToEntity(CartProductDto c) {
		CartProduct cart = new CartProduct();
		cart.setProduct(repoProd.findById(c.getProductId()).get());
		cart.setCart(repoCart.findById(c.getCartId()).get());
		cart.setPrice(c.getPrice());
		cart.setQuantity(c.getQuantity());
		return cart;

	}

	/**
	 * converte l'oggetto di tipo cartproductdto in un oggetto di tipo cartproduct
	 * restituisce l'insert della cartproduct
	 * 
	 * @param cat
	 * @return
	 */
	public CartProduct insertDto(CartProductDto cat) {
		CartProduct cartproduct = convertDtoToEntity(cat);
		cartproduct.setInsertionDate(LocalDateTime.now());
		return repo.save(cartproduct);
	}

	/**
	 * converte un oggetto di tipo cartproductDto in un'altro di tipo cartproduct
	 * restituisce l'update sul db
	 * 
	 * @param cat
	 * @return
	 */
	public CartProduct updateDto(CartProductDto cat) {

		CartProduct cartproduct = convertDtoToEntity(cat);
		CartProduct backup = new CartProduct();

		backup = repo.findByCartIdAndProductId(cat.getCartId(), cat.getProductId());
		cartproduct.setInsertionDate(backup.getInsertionDate());

		return repo.save(cartproduct);
	}

	public Iterable<CartProduct> findByCartId(Authentication auth) {
		String userMail = (String) auth.getPrincipal();
		Cart c = repoCart.findByUserId(repoUser.findByEmail(userMail).getId());
		if(c!=null) {
			Iterable<CartProduct> cartp = repo.findByCartId(c.getId());
			return cartp;
		}else {
			throw new ResourceNotFoundException("cart of user authenticated not found!");
		}
		
	}
	
	public Iterable<CartProduct> findByCartId(Integer id) {
		Iterable<CartProduct> cartp = repo.findByCartId(id);
		if(cartp.iterator().hasNext()) {
			return cartp;
		}else {
			throw new ResourceNotFoundException("No cart products for given CartId");
		}
		
	}

	public double getTotalPayment(Authentication a) {
        String CustomerMail = (String)a.getPrincipal();
		return Util.getTotalPayment(repo.findByCartId(repoCart.findByUserId(repoUser.findByEmail(CustomerMail).getId()).getId()));
	}

	public void emptyCart(Authentication auth) {
		String CustomerMail = (String) auth.getPrincipal();
		Cart userCart = repoCart.findByUserId(repoUser.findByEmail(CustomerMail).getId());

		if (userCart != null) {
			List<CartProduct> cartProd = repo.findByCartId(userCart.getId());
			cartProd.stream()
			.forEach((cprod) -> this.delete(cprod));
		}else {
			throw new ResourceNotFoundException("Cart not found for user "+ CustomerMail);
		}
	}
	
	

	public void emptyCart(Integer userId) {
		Cart userCart = repoCart.findByUserId(userId);

		if (userCart != null) {
			List<CartProduct> cartProd = repo.findByCartId(userCart.getId());
			cartProd.stream()
			.forEach((cprod) -> this.delete(cprod));
		}else {
			throw new ResourceNotFoundException("Cart not found for user ");
		}
	}

}
