package it.ecommerce.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.ecommerce.dto.CategoryDto;
import it.ecommerce.exceptions.DatabaseException;
import it.ecommerce.exceptions.NoDataFoundException;
import it.ecommerce.exceptions.ResourceNotFoundException;
import it.ecommerce.model.Category;
import it.ecommerce.repository.CategoryRepository;
import it.ecommerce.util.Util;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository repo;

	@Autowired
	private ModelMapper modelMapper;

	public Category getById(Integer id) {

		Optional<Category> c = repo.findById(id);

		if (c.isEmpty()) {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		} else {
			return c.get();
		}
	}

	public Iterable<Category> getAll() {
		Iterable<Category> c = repo.findAll();

		if (!c.iterator().hasNext()) {
			throw new NoDataFoundException();
		} else {
			return c;
		}
	}

	public Category insert(Category c) {

		c.setInsertionDate(LocalDateTime.now());
		c.setLastModified(c.getInsertionDate());

		if (repo.findByname(c.getName()) != null) {
			throw new DatabaseException("this category name already exist !");
		}
		c.setName(Util.formatString(c.getName()));
		return repo.save(c);
	}

	public Category update(Category c) {
		Category backup = new Category();
		backup = repo.findById(c.getId()).get();
		c.setInsertionDate(backup.getInsertionDate());
		c.setLastModified(LocalDateTime.now());

		if (repo.findById(c.getId()) != null) {
			if (repo.findByname(c.getName()) == null) {
				c.setName(Util.formatString(c.getName()));
				return repo.save(c);
			} else {
				throw new DatabaseException("this category name already exist !");
			}
		}
		throw new ResourceNotFoundException("The resource you are searching could not be found");
	}

	public void delete(Category c) {
		Optional<Category> cat = repo.findById(c.getId());

		if (!cat.isEmpty()) {
			repo.delete(c);

		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	/**
	 * Restituisce una lista delle categorie con un nome che contiene la stringa
	 * data come parametro
	 * 
	 * @param name
	 * @return
	 */
	public Iterable<Category> findByName(String name) {
		return repo.findBynameLike("%" + name + "%");
	}

	/**
	 * converte l'entità categoria nell'entità categoriaDto ritorna un oggettto di
	 * tipo categorydto
	 * 
	 * @param c
	 * @return
	 */
	public CategoryDto convertEntityToDto(Category c) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		CategoryDto CategoryDto = new CategoryDto();
		CategoryDto = modelMapper.map(c, CategoryDto.class);
		return CategoryDto;
	}

	/**
	 * converte l'oggetto di tipo categoriaDto in categoria ritorna un oggetto di
	 * tipo category
	 * 
	 * @param c
	 * @return
	 */
	public Category convertDtoToEntity(CategoryDto c) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		Category cat = new Category();
		cat = modelMapper.map(c, Category.class);
		return cat;

	}

	/**
	 * converte l'oggetto di tipo categorydto in un oggetto di tipo category
	 * restituisce l'insert della category
	 * 
	 * @param cat
	 * @return
	 */
	public Category insertDto(CategoryDto cat) {
		Category category = convertDtoToEntity(cat);
		category.setInsertionDate(LocalDateTime.now());
		category.setLastModified(LocalDateTime.now());
		return repo.save(category);
	}

	/**
	 * converte un oggetto di tipo categoryDto in un'altro di tipo category
	 * restituisce l'update sul db
	 * 
	 * @param cat
	 * @return
	 */
	public Category updateDto(CategoryDto cat) {

		Category category = convertDtoToEntity(cat);
		Category backup = new Category();

		backup = repo.findById(category.getId()).get();
		category.setInsertionDate(backup.getInsertionDate());
		category.setLastModified(LocalDateTime.now());

		return repo.save(category);
	}

}
