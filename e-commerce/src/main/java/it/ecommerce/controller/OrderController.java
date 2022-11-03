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

import it.ecommerce.dto.OrderDto;
import it.ecommerce.model.Order;
import it.ecommerce.security.SecurityUser;
import it.ecommerce.service.OrderService;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private static final Logger logger = LogManager.getLogger(Order.class);

	@Autowired
	private OrderService serv;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Iterable<OrderDto> findAll() {
		return serv.convertEntityToDto(serv.findAll());
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public OrderDto getById(@PathVariable("id") Integer id) {
		return serv.convertEntityToDto(serv.getById(id));
	}
	
	@GetMapping("/")
	public Iterable<OrderDto> getByUserId() {
		return serv.convertEntityToDto(serv.getAllByUserId());
	}

	@PostMapping
	public ResponseEntity<OrderDto> insert(@Validated({ OnCreate.class, Default.class }) @RequestBody OrderDto o) {
		Order order = serv.convertDtoToEntity(o);
		logger.info("Successful insertion of : {}", () -> o.toString());
		serv.insert(order);
		return new ResponseEntity<OrderDto>(o, HttpStatus.CREATED);
	}

	@PatchMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<OrderDto> update(@Validated({ OnUpdate.class, Default.class }) @RequestBody OrderDto o) {
		Order order = serv.convertDtoToEntity(o);
		logger.info("Successful update of : {}", () -> o.toString());
		serv.update(order);
		return new ResponseEntity<OrderDto>(o, HttpStatus.OK);
	}

	@DeleteMapping
	@PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
	public ResponseEntity<?> delete(@RequestBody OrderDto o, Authentication auth) {
		logger.info("Successful delete of : {}", () -> o.toString());
		Order order = serv.convertDtoToEntity(o);
		serv.delete(order,auth);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	

}
