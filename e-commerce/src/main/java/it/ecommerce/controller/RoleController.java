package it.ecommerce.controller;

import java.util.List;

import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import it.ecommerce.dto.RoleDto;
import it.ecommerce.model.PersonalData;
import it.ecommerce.model.Role;
import it.ecommerce.service.RoleService;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleService serv;

	private static final Logger logger = LogManager.getLogger(PersonalData.class);

	@GetMapping("/{id}")
	public Role getById(@PathVariable("id") Integer id) {
		return serv.getById(id);
	}

	@GetMapping
	public Iterable<Role> getAll() {
		return serv.getAll();
	}

	@PostMapping
	public ResponseEntity<Role> insert(@Validated({ Default.class, OnCreate.class })@RequestBody RoleDto r) {
		Role role = serv.convertDtoToEntity(r);
		serv.insert(role);
		logger.info("Successful insert {} !", () -> role.toString());
		return new ResponseEntity<Role>(role, HttpStatus.CREATED);
	}

	@PatchMapping
	public ResponseEntity<Role> update(@Validated({ Default.class, OnUpdate.class }) @RequestBody RoleDto r) {
		Role role = serv.convertDtoToEntity(r);
		serv.update(role);
		logger.info("Successful delete {} !", () -> role.toString());
		return new ResponseEntity<Role>(role, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<?> delete(@RequestBody Role r) {
		serv.delete(r);
		logger.info("Successful delete {} !", () -> r.toString());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/")
	public List<Role> findrolebynamelike(@RequestParam String name) {
		return serv.getRoleByNameLike(name);
	}

}
