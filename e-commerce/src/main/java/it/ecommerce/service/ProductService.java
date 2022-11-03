package it.ecommerce.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.ecommerce.dto.ProductDto;
import it.ecommerce.exceptions.NoDataFoundException;
import it.ecommerce.exceptions.ResourceNotFoundException;
import it.ecommerce.fileManagement.FileStorageProperties;
import it.ecommerce.fileManagement.FileStorageService;
import it.ecommerce.model.Product;
import it.ecommerce.repository.ProductRepository;
import it.ecommerce.repository.PromotionRepository;
import it.ecommerce.repository.UserRepository;
import it.ecommerce.util.Util;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private PromotionRepository repoP;
	
	@Autowired 
	private UserRepository repoU;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private FileStorageService fileServ;
	
	@Autowired
	private FileStorageProperties fileProp;

	public Iterable<Product> findAll() {
		Iterable<Product> list = repo.findAll();
		if(list.iterator().hasNext()) {
			return list;
		} else {
			throw new NoDataFoundException();
		}
	}

	public Product getById(Integer id) {
		Optional<Product> p = repo.findById(id);
		if (!p.isEmpty()) {
			return repo.findById(id).get();
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	public Product insert(Product p, MultipartFile file) {
		fileServ.storeFile(file);
		p.setImage(fileProp.getUploadDir()+"/"+file.getOriginalFilename());
		p.setName(Util.formatString(p.getName()));
		p.setInsertionDate(LocalDateTime.now());
		p.setLastModified(p.getInsertionDate());
		return repo.save(p);
	}

	public Product update(Product p) {
		Optional<Product> optP = repo.findById(p.getId());
		if (!optP.isEmpty()) {
			Product backup = repo.findById(p.getId()).get();
			p.setInsertionDate(backup.getInsertionDate());
			p.setLastModified(LocalDateTime.now());
			p.setName(Util.formatString(p.getName()));
			return repo.save(p);
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	public void delete(Product p) {
		Optional<Product> optP = repo.findById(p.getId());
		if (!optP.isEmpty()) {
			repo.delete(p);
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	/**
	 * Restituisce una lista di tutti i prodotti selezionati dal nome della
	 * categoria
	 * 
	 * @param category
	 * @return
	 */
	public Iterable<Product> getAllByCategoryName(String category) {
	    Iterable<Product> list= repo.getAllByCategoryName(category);
		if(list.iterator().hasNext()) {
			return list;
		} else {
			throw new ResourceNotFoundException("category not found");
		}
	}

	/**
	 * converte l'entità Product nell'entità ProductDto ritorna un oggettto di tipo
	 * ProductDto
	 * 
	 * @param c
	 * @return
	 */
	public ProductDto convertEntityToDto(Product p) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		ProductDto ProductDto = new ProductDto();
		ProductDto = modelMapper.map(p, ProductDto.class);
		return ProductDto;
	}
	
	/**
	 * converte l'iterable di entità in iterable di dto
	 * @param listp
	 * @return
	 */
	
	public Iterable<ProductDto> convertEntityToDto(Iterable <Product> listp) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		ProductDto ProductDto = new ProductDto();
		List<ProductDto> listDto = new ArrayList<ProductDto>();
		for(Product p: listp) {
			ProductDto = modelMapper.map(p, ProductDto.class);	
		   listDto.add(ProductDto);
		}
		return listDto;
	}

	/**
	 * converte l'oggetto di tipo ProductDto in Product ritorna un oggetto di tipo
	 * Product
	 * 
	 * @param c
	 * @return
	 */
	public Product convertDtoToEntity(ProductDto p) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		Product prod = new Product();
		prod = modelMapper.map(p, Product.class);
		prod.setDeleted(false);
		return prod;

	}

	/**
	 * converte l'oggetto di tipo ProductDto in un oggetto di tipo Product
	 * restituisce l'insert del Product
	 * 
	 * @param cat
	 * @return
	 */
	public Product insertDto(ProductDto prod) {
		Product product = convertDtoToEntity(prod);
		product.setInsertionDate(LocalDateTime.now());
		product.setLastModified(LocalDateTime.now());
		return repo.save(product);
	}

	/**
	 * converte un oggetto di tipo ProductDto in un'altro di tipo Product
	 * restituisce l'update sul db
	 * 
	 * @param cat
	 * @return
	 */
	public Product updateDto(ProductDto prod) {

		Product product = convertDtoToEntity(prod);
		Product backup = new Product();

		backup = repo.findById(product.getId()).get();
		product.setInsertionDate(backup.getInsertionDate());
		product.setLastModified(LocalDateTime.now());

		return repo.save(product);
	}
	
	public  double getDiscountPrice(Product prod, Integer userId) {
		double startPrice=prod.getPrice();
		Integer productDiscount;
		Integer personalDiscount;
		if(repoP.getProductDiscount(prod,java.sql.Date.valueOf(LocalDate.now()))!=null)
		{
		 productDiscount=repoP.getProductDiscount(prod,java.sql.Date.valueOf(LocalDate.now())).getDiscount();
		} else {
			productDiscount=null;
		}
		if(repoP.getPersonalDiscount(prod,repoU.findById(userId).get(),java.sql.Date.valueOf(LocalDate.now()))!=null)
		{
		personalDiscount=repoP.getPersonalDiscount(prod,repoU.findById(userId).get(),java.sql.Date.valueOf(LocalDate.now())).getDiscount();	
		} else {
			personalDiscount=null;
		}			
		return Util.calcDiscount(startPrice, productDiscount, personalDiscount);
	}

}
