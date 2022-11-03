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

import it.ecommerce.dto.CategoryDto;
import it.ecommerce.model.Category;
import it.ecommerce.service.CategoryService;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	private static final Logger logger = LogManager.getLogger(Category.class);
			
	@Autowired
	private CategoryService serv;

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Category getById(@PathVariable("id") Integer id) {
		return serv.getById(id);
	}

	@GetMapping
	public Iterable<Category> getAll() {
		return serv.getAll();
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Category> insert(@Validated({ OnCreate.class, Default.class }) @RequestBody CategoryDto c) {
		Category cat = serv.convertDtoToEntity(c);
		logger.info("Successful insertion of : {}", ()->c.toString());
		serv.insert(cat);
		return new ResponseEntity<Category>(cat, HttpStatus.CREATED);
	}

	@PatchMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Category> update(@Validated({ OnUpdate.class, Default.class }) @RequestBody CategoryDto c) {
		Category cat = serv.convertDtoToEntity(c);
		logger.info("Successful update of : {}", ()->c.toString());
		serv.update(cat);
		return new ResponseEntity<Category>(cat, HttpStatus.OK);
	}

	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@RequestBody Category c) {
		serv.delete(c);
		logger.info("Successful delete of : {}", ()->c.toString());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/")
	public Iterable<Category> getByName(@RequestParam("name") String name) {
		return serv.findByName(name);
	}

}
