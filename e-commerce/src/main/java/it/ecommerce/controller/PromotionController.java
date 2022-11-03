package it.ecommerce.controller;


import javax.validation.Valid;
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

import it.ecommerce.dto.PromotionDto;
import it.ecommerce.model.PersonalData;
import it.ecommerce.model.Promotion;
import it.ecommerce.service.PromotionService;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

	@Autowired
	private PromotionService serv;

	private static final Logger logger = LogManager.getLogger(PersonalData.class);

//	@GetMapping
//	public Iterable<PromotionDto> findAll() {
//		return serv.convertEntityToDto(serv.findAll());
//	}
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Iterable<PromotionDto> findAll() {
		return serv.convertEntityToDto(serv.findAll());
	}


	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public PromotionDto getById(@PathVariable("id") Integer id) {
		return serv.convertEntityToDto(serv.getById(id));
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PromotionDto> insert( @Validated({ Default.class, OnCreate.class })@RequestBody PromotionDto p) {
		Promotion prom = serv.convertDtoToEntity(p);
		serv.insert(prom);
		logger.info("Successful insert {} !", () -> prom.toString());
		return new ResponseEntity<PromotionDto>(p, HttpStatus.CREATED);

	}

	@PatchMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PromotionDto> update(@Validated({ Default.class, OnUpdate.class }) @RequestBody PromotionDto p) {
		Promotion prom = serv.convertDtoToEntity(p);
		serv.update(prom);
		logger.info("Successful update {} !", () -> prom.toString());
		return new ResponseEntity<PromotionDto>(p, HttpStatus.OK);
	}

	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@RequestBody Promotion p) {
		serv.delete(p);
		logger.info("Successful delete {} !", () -> p.toString());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
