package it.ecommerce.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.ecommerce.dto.ProductDto;
import it.ecommerce.model.Product;
import it.ecommerce.repository.ProductRepository;
import it.ecommerce.repository.UserRepository;
import it.ecommerce.security.SecurityUser;
import it.ecommerce.service.ProductService;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService serv;
	
	@Autowired ProductRepository repo;
	
	@Autowired
	UserRepository repoUser;

	private static final Logger logger = LogManager.getLogger(ProductController.class);

	@GetMapping
	public Iterable<ProductDto> findAll(Authentication auth) {
		Iterable<ProductDto> listDto=serv.convertEntityToDto(serv.findAll());
		String s = (String) auth.getPrincipal();	
		for(ProductDto p: listDto) {
			p.setPrice(serv.getDiscountPrice(repo.findById(p.getId()).get(),repoUser.findByEmail(s).getId() ));
		}
		return listDto;
	}
	
//	@GetMapping
//	public Iterable<Product> findAll() {
//		return serv.findAll();
//	}


	@GetMapping("/{id}")
	public ProductDto getById(@PathVariable("id") Integer id, Authentication auth) {
		ProductDto p = serv.convertEntityToDto(serv.getById(id));
		SecurityUser s = (SecurityUser) auth.getPrincipal();
		p.setPrice(serv.getDiscountPrice(repo.findById(p.getId()).get(), s.getId()));
		return p;
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Product> insert(@Validated({ OnCreate.class, Default.class }) @RequestPart("data") ProductDto p, @RequestPart("file") MultipartFile file) {
		Product pr = serv.convertDtoToEntity(p);
		serv.insert(pr, file);
		logger.info("Successful insert {} !", () -> pr.toString());
		return new ResponseEntity<Product>(pr, HttpStatus.CREATED);
	}

	@PatchMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Product> update(@Validated({ OnUpdate.class, Default.class }) @RequestBody ProductDto p) {
		Product pr = serv.convertDtoToEntity(p);
		serv.update(pr);
		logger.info("Successful update {} !", () -> pr.toString());
		return new ResponseEntity<Product>(pr, HttpStatus.OK);
	}

	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@RequestBody Product p) {
		serv.delete(p);
		logger.info("Successful delete {} !", () -> p.toString());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/")
	public Iterable<ProductDto> getAllByCategoryName(@RequestParam("category") String c, Authentication auth) {	
		Iterable<ProductDto> listDto=serv.convertEntityToDto(serv.getAllByCategoryName(c));
		SecurityUser s = (SecurityUser) auth.getPrincipal();	
		for(ProductDto p: listDto) {
			p.setPrice(serv.getDiscountPrice(repo.findById(p.getId()).get(), s.getId()));
		}
		return listDto;
	}
	

//	@GetMapping("/")
//	public Iterable<Product> getAllByCategoryName(@RequestParam("category") String c) {	
//		return serv.getAllByCategoryName(c);
//	}
//	
	@GetMapping("/get-discount-price/{prod}")
	public double getDiscountPrice(Authentication auth,@PathVariable(name = "prod") Integer prod) {
		SecurityUser s = (SecurityUser) auth.getPrincipal();		
		return serv.getDiscountPrice(repo.findById(prod).get(), s.getId());
	}

}
