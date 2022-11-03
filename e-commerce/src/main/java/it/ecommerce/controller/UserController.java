package it.ecommerce.controller;

import java.util.Map;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.ecommerce.dto.UserDto;
import it.ecommerce.model.PersonalData;
import it.ecommerce.model.User;
import it.ecommerce.service.EmailSenderService;
import it.ecommerce.service.UserService;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;
import lombok.NonNull;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService serv;
	
	@Autowired
	private EmailSenderService emailSenderServ;

	private static final Logger logger = LogManager.getLogger(UserController.class);

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public UserDto getById(@PathVariable("id") Integer id) {
		return serv.convertEntityToDto(serv.getById(id));
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Iterable<UserDto> getAll() {
		return serv.convertEntityToDto(serv.getAll());
	}

	@PostMapping
	@PreAuthorize("permitAll()")
	public ResponseEntity<UserDto> insert(@Validated({ Default.class, OnCreate.class }) @RequestBody UserDto u) throws MessagingException {
		User us = serv.convertDtoToEntity(u);
		serv.insert(us);
		logger.info("Successful insert {} !", () -> us.toString());
		emailSenderServ.SignUpMessage(us);
		return new ResponseEntity<UserDto>(u, HttpStatus.CREATED);
	}

	@PatchMapping
	@PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
	public ResponseEntity<UserDto> update(@Validated({ Default.class, OnUpdate.class }) @RequestBody UserDto u) {
		User us = serv.convertDtoToEntity(u);
		serv.update(us);
		logger.info("Successful update {} !", () -> us.toString());
		return new ResponseEntity<UserDto>(u, HttpStatus.OK);
	}

	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@RequestBody User u) {
		serv.delete(u);
		logger.info("Successful delete {} !", () -> u.toString());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * ha in ingresso l'identificativo del ruolo restituisce un'iterable di utenti
	 * 
	 * @param roleId
	 * @return
	 */
	@GetMapping("/get-users-by-role/{roleId}")
	@PreAuthorize("hasRole('ADMIN')")
	public Iterable<UserDto> getUsersByRoleId(@PathVariable("roleId") Integer roleId) {
		return serv.convertEntityToDto(serv.getUsersByRoleId(roleId));
	}

	/**
	 * ha in ingresso una mail restituisce un utente
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping("/get-by-email/")
	@PreAuthorize("hasRole('ADMIN')")
	public User getByEmail(@RequestParam("email") String email) {
		return serv.getByEmail(email);
	}

//	@PostMapping("/test")
//	@PreAuthorize("hasRole('ADMIN')")
//	public User insertCheckingEmail(@Valid @RequestBody UserDto u) {
//		User us = serv.convertDtoToEntity(u);
//		return serv.insertCheckingEmail(us);
//	}

	@PatchMapping("/password-change")
	@PreAuthorize("permitAll()")
	public ResponseEntity<?> passwordChange(@RequestBody @NotNull Map<String, String> json) {
		serv.changePassword(json.get("email"), json.get("oldpass"), json.get("newpass"));
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
