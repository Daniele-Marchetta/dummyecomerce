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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.ecommerce.dto.ProvinceDto;
import it.ecommerce.model.Province;

import it.ecommerce.service.ProvinceService;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;

@RestController
@RequestMapping("/provincies")
public class ProvinceController {

	@Autowired
	private ProvinceService serv;

	private static final Logger logger = LogManager.getLogger(Province.class);

	@GetMapping
	public Iterable<ProvinceDto> findAll() {
		Iterable<ProvinceDto> listDto=serv.convertEntityToDto(serv.findAll());
	      return listDto;
	}

	@GetMapping("/{acronym}")
	@PreAuthorize("hasRole('ADMIN')")
	public ProvinceDto getById(@PathVariable("acronym") String ac) {
		return serv.convertEntityToDto(serv.getById(ac));
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProvinceDto> insert(@Validated({ OnCreate.class, Default.class }) @RequestBody ProvinceDto p) {
		Province prov = serv.convertDtoToEntity(p);
		serv.insert(prov);
		logger.info("Successful insert {} !", () -> p.toString());
		return new ResponseEntity<ProvinceDto>(p, HttpStatus.CREATED);
	}

	@PatchMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProvinceDto> update( @Validated({ OnUpdate.class, Default.class })@RequestBody ProvinceDto p) {
		Province prov = serv.convertDtoToEntity(p);
		serv.update(prov);
		logger.info("Successful update {} !", () -> p.toString());
		return new ResponseEntity<ProvinceDto>(p, HttpStatus.OK);
	}

	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@RequestBody Province p) {
		serv.delete(p);
		logger.info("Successful delete {} !", () -> p.toString());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/")
	public Iterable<ProvinceDto> GetByNameLike(@RequestParam String name) {
		return serv.convertEntityToDto(serv.GetByNameLike(name));
	}

}
