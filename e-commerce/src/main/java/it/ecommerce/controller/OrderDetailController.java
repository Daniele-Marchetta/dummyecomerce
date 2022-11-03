package it.ecommerce.controller;

import javax.validation.groups.Default;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.ecommerce.dto.OrderDetailDto;
import it.ecommerce.model.OrderDetail;
import it.ecommerce.service.OrderDetailService;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;

@RestController
@RequestMapping("/order-details")
public class OrderDetailController {

	private static final Logger logger = LogManager.getLogger(OrderDetail.class);

	@Autowired
	private OrderDetailService serv;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Iterable<OrderDetailDto> findAll() {
		Iterable<OrderDetailDto> ord = serv.convertEntityToDto(serv.findAll());
		return ord;
	}

	@GetMapping("/{orderId}/{productId}")
	@PreAuthorize("hasRole('ADMIN')")
	public OrderDetailDto getById(@PathVariable("orderId") Integer orderId,
			@PathVariable("productId") Integer productId) {
		return serv.convertEntityToDto(serv.getById(orderId, productId));
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<OrderDetail> insert(@Validated({ OnCreate.class, Default.class }) @RequestBody OrderDetailDto o) {
		OrderDetail orderdetail = serv.convertDtoToEntity(o);
		logger.info("Successful insertion of : {}", () -> o.toString());
		serv.insert(orderdetail);
		return new ResponseEntity<OrderDetail>(orderdetail, HttpStatus.CREATED);
	}

	@PatchMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<OrderDetail> update(@Validated({ OnUpdate.class, Default.class }) @RequestBody OrderDetailDto o) {
		OrderDetail orderdetail = serv.convertDtoToEntity(o);
		logger.info("Successful insertion of : {}", () -> o.toString());
		serv.update(orderdetail);
		return new ResponseEntity<OrderDetail>(orderdetail, HttpStatus.OK);
	}

	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@RequestBody OrderDetail o) {
		logger.info("Successful insertion of : {}", () -> o.toString());
		serv.delete(o);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/get-all-by-order-id/{orderId}")
	public Iterable<OrderDetailDto> getAllByOrderId(@PathVariable("orderId") Integer orderId) {
		Iterable<OrderDetailDto> list = serv.convertEntityToDto(serv.getAllByOrderId(orderId));
		return list;
	}

}