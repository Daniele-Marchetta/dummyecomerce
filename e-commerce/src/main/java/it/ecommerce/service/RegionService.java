package it.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.ecommerce.dto.RegionDto;
import it.ecommerce.exceptions.DatabaseException;
import it.ecommerce.exceptions.NoDataFoundException;
import it.ecommerce.exceptions.ResourceNotFoundException;
import it.ecommerce.model.Region;
import it.ecommerce.repository.RegionRepository;
import it.ecommerce.util.Util;

@Service
@Transactional
public class RegionService {

	@Autowired
	private RegionRepository repo;

	@Autowired
	private ModelMapper modelMapper;

	public Iterable<Region> findAll() {
		Iterable<Region> list = repo.findAll();
		if (list.iterator().hasNext()) {
			return repo.findAll();
		} else {
			throw new NoDataFoundException();
		}
	}

	public Region getById(Integer id) {
		Optional<Region> r = repo.findById(id);
		if (!r.isEmpty()) {
			return repo.findById(id).get();
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	public void delete(Region r) {
		Optional<Region> optR = repo.findById(r.getId());
		if (!optR.isEmpty()) {
			repo.delete(r);
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	public Region insert(Region r) {
		if (repo.findByName(r.getName()) == null) {
			r.setName(Util.formatString(r.getName()));
			return repo.save(r);
		} else {
			throw new DatabaseException("Cannot insert new region, there is already one with same name");
		}
	}

	public Region update(Region r) {
		Optional<Region> optR = repo.findById(r.getId());
		if (!optR.isEmpty()) {
			r.setName(Util.formatString(r.getName()));
			return repo.save(r);
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	public List<Region> getregionbynamelike(String name) {

		List<Region> r = repo.findByNameLike("%" + name + "%");

		if (!r.isEmpty()) {
			return r;
		} else {
			throw new ResourceNotFoundException("The region you are searching could not be found");
		}
	}

	public Region convertDtoToEntity(RegionDto p) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		Region reg = new Region();

		reg = modelMapper.map(p, Region.class);

		return reg;

	}
	
	public RegionDto convertEntityToDto(Region r) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		RegionDto rdto = new RegionDto();
		rdto = modelMapper.map(r, RegionDto.class);
		return rdto;
	}
	
	
	public Iterable<RegionDto> convertEntityToDto(Iterable <Region> listp) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        RegionDto RegionDto = new RegionDto();
        List<RegionDto> listDto = new ArrayList<RegionDto>();
        for(Region p: listp) {
            RegionDto = modelMapper.map(p, RegionDto.class);  
           listDto.add(RegionDto);
        }
        return listDto;
    }
	
	

}