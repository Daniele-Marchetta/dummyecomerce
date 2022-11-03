package it.ecommerce.controller;

import javax.validation.groups.Default;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.ecommerce.dto.CartProductDto;
import it.ecommerce.model.CartProduct;
import it.ecommerce.repository.CartRepository;
import it.ecommerce.security.SecurityUser;
import it.ecommerce.service.CartProductService;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;

@RestController
@RequestMapping("/cart-products")
public class CartProductController {

	private static final Logger logger = LogManager.getLogger(CartProduct.class);

	@Autowired
	private CartProductService serv;
	
	@Autowired
	private CartRepository repoC;
	

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{cartId}/{productId}")
	public CartProductDto getById(@PathVariable("cartId") Integer cartId, @PathVariable("productId") Integer productId) {
		CartProductDto c = serv.convertEntityToDto(serv.getById(cartId, productId));
		return c;
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Iterable<CartProductDto> getAll() {
		Iterable<CartProductDto> listDto = serv.convertEntityToDto(serv.getAll());
		return listDto;
	}

	@PostMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CartProductDto> insertAdmin(
			@Validated({ OnCreate.class, Default.class }) @RequestBody CartProductDto c) {
		CartProduct cartp = serv.convertDtoToEntity(c);
		logger.info("Successful insertion of : {}", () -> c.toString());
		serv.insert(cartp);
		return new ResponseEntity<CartProductDto>(c, HttpStatus.CREATED);
	}
	@PostMapping
	public ResponseEntity<CartProductDto> insertCustomer(
			@Validated({ OnCreate.class, Default.class }) @RequestBody CartProductDto c) {
		CartProduct cartp = serv.convertDtoToEntity(c);
		serv.insert(cartp,SecurityContextHolder.getContext().getAuthentication());
		logger.info("Successful insertion of : {}", () -> c.toString());
		return new ResponseEntity<CartProductDto>(c, HttpStatus.CREATED);
	}

	@PatchMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CartProductDto> updateAdmin(
			@Validated({ OnUpdate.class, Default.class }) @RequestBody CartProductDto c) {
		CartProduct cartp = serv.convertDtoToEntity(c);
		serv.update(cartp);
		logger.info("Successful update of : {}", () -> c.toString());
		return new ResponseEntity<CartProductDto>(c, HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<CartProductDto> updateCustomer(
			@Validated({ OnUpdate.class, Default.class }) @RequestBody CartProductDto c) {
		CartProduct cartp = serv.convertDtoToEntity(c);
		serv.update(cartp,SecurityContextHolder.getContext().getAuthentication());
		logger.info("Successful update of : {}", () -> c.toString());
		return new ResponseEntity<CartProductDto>(c, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<?> delete(@RequestBody CartProduct c) {
		logger.info("Successful delete of : {}", () -> c.toString());
		serv.delete(c);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{cartId}")
	@PreAuthorize("hasRole('ADMIN')")
	public Iterable<CartProductDto> getProductsByCartId(@PathVariable("cartId") Integer cartId) {
		Iterable<CartProductDto> cpdto = serv.convertEntityToDto(serv.findByCartId(cartId));
		return cpdto;
	}

	/**
	 * 
	 * @param auth utente autenticato
	 * @return lista di prodotti nel carrello utente
	 */
	
	@GetMapping("/")
	@PreAuthorize("hasRole('CUSTOMER')")
	public Iterable<CartProductDto> getProductsByCartId() {
		Iterable<CartProductDto> cpdto = serv.convertEntityToDto(serv.findByCartId(SecurityContextHolder.getContext().getAuthentication()));
		return cpdto;
	}
	
	@GetMapping("/get-total-payment")
	@PreAuthorize("hasRole('CUSTOMER')")
	public double getTotalPayment(Authentication auth) {
		return serv.getTotalPayment(auth);
	}
	
	@DeleteMapping("/delete-all")
	public ResponseEntity<?> emptyCart() {
		serv.emptyCart(SecurityContextHolder.getContext().getAuthentication());
		logger.info("Successful delete all cart Items !!");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
