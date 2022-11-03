package it.ecommerce.controller;

import javax.validation.groups.Default;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.ecommerce.dto.CartDto;
import it.ecommerce.model.Cart;
import it.ecommerce.service.CartService;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;

@RestController
@RequestMapping("/carts")
public class CartController {
	private static final Logger logger = LogManager.getLogger(Cart.class);

	@Autowired
	private CartService serv;

	@GetMapping("/{id}")
	public CartDto getById(@PathVariable("id") Integer id) {
		CartDto c= serv.convertEntityToDto(serv.getById(id));
		return c;
	}

	@GetMapping
	public Iterable<CartDto> getAll() {
		Iterable<CartDto> listDto = serv.convertEntityToDto(serv.getAll());
		return listDto;
	}

	@PostMapping
	public ResponseEntity<Cart> insert(@Validated({ OnCreate.class, Default.class })@RequestBody CartDto c) {
		Cart cart = serv.convertDtoToEntity(c);
		serv.insert(cart);
		logger.info("Successful insertion of : {}", ()->cart.toString());
		return new ResponseEntity<Cart>(cart, HttpStatus.CREATED);

	}

	@PatchMapping
	public ResponseEntity<Cart> update(@Validated({ OnUpdate.class, Default.class }) @RequestBody CartDto c) {
		Cart cart = serv.convertDtoToEntity(c);
		serv.update(cart);
		logger.info("Successful update of : {}", ()->cart.toString());
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<?> delete(@RequestBody Cart c) {
		serv.delete(c);
		logger.info("Successful delete of : {}", ()->c.toString());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	

}
