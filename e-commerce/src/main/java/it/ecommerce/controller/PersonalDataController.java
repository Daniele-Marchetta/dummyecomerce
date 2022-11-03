package it.ecommerce.controller;


import javax.validation.groups.Default;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.ecommerce.dto.PersonalDataDto;
import it.ecommerce.model.PersonalData;
import it.ecommerce.service.PersonalDataService;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;

@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "Access-Control-Allow-Origin")
@RestController
@RequestMapping("/personal-data")
public class PersonalDataController {
	
	@Autowired
	private PersonalDataService serv;
	
	private static final Logger logger = LogManager.getLogger(PersonalData.class);

	

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Iterable<PersonalDataDto> findAll() {
		return serv.convertEntityToDto(serv.findAll());
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
	public PersonalDataDto getById(@PathVariable("id") Integer id) {
		return serv.convertEntityToDto(serv.getById(id));
	}

	@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "Access-Control-Allow-Origin")
	@PostMapping
	@PreAuthorize("permitAll()")
	public ResponseEntity<PersonalData> insert(@Validated({ OnCreate.class, Default.class }) @RequestBody PersonalDataDto p) {
		PersonalData pers=serv.convertDtoToEntity(p);
		serv.insert(pers);
		logger.info("Successful insert {} !",()->pers.toString());
		return new ResponseEntity<PersonalData>(pers, HttpStatus.CREATED);
	}

	@PatchMapping
	@PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
	public ResponseEntity<PersonalData> update(@Validated({ OnUpdate.class, Default.class }) @RequestBody PersonalDataDto p ) {
		PersonalData pers=serv.convertDtoToEntity(p);
		serv.update(pers);
		logger.info("Successful update {} !",()->pers.toString());
		return new ResponseEntity<PersonalData>(pers, HttpStatus.OK);
	}

	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@RequestBody PersonalDataDto p) {
		PersonalData pers=serv.convertDtoToEntity(p);
		serv.delete(pers);
		logger.info("Successful delete {} !",()->p.toString());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
