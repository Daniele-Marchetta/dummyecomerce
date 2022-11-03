package it.ecommerce.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.ecommerce.dto.BrandDto;
import it.ecommerce.exceptions.DatabaseException;
import it.ecommerce.exceptions.NoDataFoundException;
import it.ecommerce.exceptions.ResourceNotFoundException;
import it.ecommerce.model.Brand;
import it.ecommerce.repository.BrandRepository;
import it.ecommerce.util.Util;

@Service
@Transactional
public class BrandService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private BrandRepository repo;

	public Brand convertDtoToEntity(BrandDto b) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		Brand brand = new Brand();
		brand = modelMapper.map(b, Brand.class);
		return brand;

	}

	public Iterable<Brand> findAll() {

		Iterable<Brand> iter = repo.findAll();

		if (iter.iterator().hasNext()) {
			return iter;
		} else {
			throw new NoDataFoundException();
		}

	}

	public Brand getById(Integer id) {
		Optional<Brand> b = repo.findById(id);
		if (!b.isEmpty()) {
			return b.get();
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	public Brand insert(Brand b) {
		if (repo.findByName(b.getName()) == null) {
			b.setName(Util.formatString(b.getName()));
			return repo.save(b);
		} else {
			throw new DatabaseException("Already exist brand name");
		}

	}

	public Brand update(Brand b) {
		Optional<Brand> brand = repo.findById(b.getId());
		if (brand.isEmpty()) {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		} else {
			if (repo.findByName(b.getName()) == null) {
				b.setName(Util.formatString(b.getName()));
				return repo.save(b);
			} else {
				throw new DatabaseException("Already exist brand name");
			}
		}

	}

	public void delete(Brand b) {
		Optional<Brand> brand = repo.findById(b.getId());

		if (brand.isEmpty()) {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		} else {
			repo.delete(b);
		}

	}

	public List<Brand> findBrandByNameLike(String name) {
		List<Brand> b = repo.findAllByNameLike("%"+name+"%");
		if (!b.isEmpty()) {
			return b;
		} else {
			throw new ResourceNotFoundException("Brand name like " + name + " not found !!");
		}
	}

}
