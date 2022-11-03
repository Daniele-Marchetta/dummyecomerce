package it.ecommerce.controller;

import javax.validation.Valid;
import javax.validation.groups.Default;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.ecommerce.dto.CreditCardDto;
import it.ecommerce.model.CreditCard;
import it.ecommerce.security.SecurityUser;
import it.ecommerce.service.CreditCardService;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;

@RestController
@RequestMapping("/credit-cards")
public class CreditCardController {

	private static final Logger logger = LogManager.getLogger(CreditCard.class);

	@Autowired
	private CreditCardService serv;

	@GetMapping("/{userId}/{cardNum}")
	@PreAuthorize("hasRole('ADMIN')")
	public CreditCardDto getById(@PathVariable("userId") Integer userId, @PathVariable("cardNum") String cardNum) {
		return serv.convertEntityToDto(serv.getById(userId, cardNum));
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Iterable<CreditCardDto> getAll() {
		return serv.convertEntityToDto(serv.getAll());
	}

	@PostMapping
	public ResponseEntity<CreditCardDto> insert(@Validated({ OnCreate.class, Default.class }) @RequestBody CreditCardDto c) {
		CreditCard cred = serv.convertDtoToEntity(c);
		logger.info("Successful insert of : {}", () -> c.toString());
		serv.insert(cred);
		return new ResponseEntity<CreditCardDto>(c, HttpStatus.CREATED);
	}

	@PatchMapping
	public ResponseEntity<CreditCardDto> update(@Validated({ OnUpdate.class, Default.class }) @RequestBody CreditCardDto c) {
		CreditCard cred = serv.convertDtoToEntity(c);
		logger.info("Successful update of : {}", () -> c.toString());
		serv.update(cred);
		return new ResponseEntity<CreditCardDto>(c, HttpStatus.OK);

	}

	@DeleteMapping
	public ResponseEntity<?> delete(@RequestBody CreditCardDto c) {
		CreditCard cred = serv.convertDtoToEntity(c);
		logger.info("Successful delete of : {}", () -> c.toString());
		serv.delete(cred);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public Iterable<CreditCardDto> findAllByUserIdAdmin(@PathVariable("userId") Integer userId) {
		return serv.convertEntityToDto(serv.findAllByUserId(userId));
	}

	@GetMapping("/")
	@PreAuthorize("hasRole('CUSTOMER')")
	public Iterable<CreditCardDto> findAllByUserIdCustomer(Authentication auth) {
		return serv.convertEntityToDto(serv.findAllByUserId(auth));
	}
}
