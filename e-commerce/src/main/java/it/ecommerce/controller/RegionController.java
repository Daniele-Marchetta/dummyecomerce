package it.ecommerce.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.ecommerce.dto.RegionDto;
import it.ecommerce.model.PersonalData;
import it.ecommerce.model.Region;
import it.ecommerce.service.RegionService;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;

@RestController
@RequestMapping("/regions")
public class RegionController {

	@Autowired
	private RegionService serv;

	private static final Logger logger = LogManager.getLogger(PersonalData.class);

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Iterable<RegionDto> findAll() {
		Iterable<RegionDto> listDto = serv.convertEntityToDto(serv.findAll());
		return listDto;
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public RegionDto getById(@PathVariable("id") Integer id) {
		return serv.convertEntityToDto(serv.getById(id));
	}

	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Region> delete(@RequestBody Region r) {
		serv.delete(r);
		logger.info("Successful delete {} !", () -> r.toString());
		return new ResponseEntity<Region>(HttpStatus.NO_CONTENT);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<RegionDto> insert(@Validated({ OnCreate.class, Default.class }) @RequestBody RegionDto r) {
		Region reg = serv.convertDtoToEntity(r);
		serv.insert(reg);
		logger.info("Successful insert {} !", () -> r.toString());
		return new ResponseEntity<RegionDto>(r, HttpStatus.CREATED);
	}

	@PatchMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<RegionDto> update(@Validated({ OnCreate.class, Default.class }) @RequestBody RegionDto r) {
		Region reg = serv.convertDtoToEntity(r);
		serv.update(reg);
		logger.info("Successful update {} !", () -> r.toString());
		return new ResponseEntity<RegionDto>(r, HttpStatus.OK);
	}

	@GetMapping("/")
	public List<Region> getregionbynamelike(@RequestParam String name) {
		return serv.getregionbynamelike(name);
	}

}
