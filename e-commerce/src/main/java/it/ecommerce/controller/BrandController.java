package it.ecommerce.controller;


import java.util.List;

import javax.validation.Valid;
import javax.validation.groups.Default;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

import it.ecommerce.dto.BrandDto;
import it.ecommerce.model.Brand;
import it.ecommerce.service.BrandService;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;

@RestController
@RequestMapping("/brands")
public class BrandController {
	
	private static final Logger logger = LogManager.getLogger(BrandController.class);

	@Autowired
	private BrandService serv;

	@GetMapping
	public Iterable<Brand> findAll() {

		return serv.findAll();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Brand getById(@PathVariable("id") Integer id) {
		return serv.getById(id);

	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Brand> insert(@Validated({ OnCreate.class, Default.class }) @RequestBody BrandDto b) {
		Brand brand= serv.convertDtoToEntity(b);
		serv.insert(brand);
		logger.info("Successful insertion of : {}", ()->brand.getName());
		return new ResponseEntity<Brand>(brand, HttpStatus.CREATED);
	}

	@PatchMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Brand> update(@Validated({ OnUpdate.class, Default.class }) @RequestBody BrandDto b) {
		Brand brand= serv.convertDtoToEntity(b);
		serv.update(brand);
		logger.info("Successful update of : {}", ()->brand.getName());
		return new ResponseEntity<Brand>(brand, HttpStatus.OK);
	}

	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@RequestBody Brand b) {
		serv.delete(b);
		logger.info("Successful deletion of : {}", ()->b.getName());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@GetMapping("/")
	public List<Brand> getBrandByNameLike(@RequestParam String name) {
		List<Brand> brandName = serv.findBrandByNameLike(name);
		logger.info("Successful result search of : {}", ()->brandName);
	   return brandName;
	   }
}
