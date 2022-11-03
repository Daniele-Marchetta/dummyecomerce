package it.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.ecommerce.dto.ProvinceDto;
import it.ecommerce.exceptions.DatabaseException;
import it.ecommerce.exceptions.NoDataFoundException;
import it.ecommerce.exceptions.ResourceNotFoundException;
import it.ecommerce.model.Province;
import it.ecommerce.model.Region;
import it.ecommerce.repository.ProvinceRepository;
import it.ecommerce.util.Util;

@Service
@Transactional
public class ProvinceService {

	@Autowired
	private ProvinceRepository repo;

	@Autowired
	private ModelMapper modelMapper;

	public Iterable<Province> findAll() {
		Iterable<Province> list = repo.findAll();
		if (list.iterator().hasNext()) {
			return repo.findAll();
		} else {
			throw new NoDataFoundException();
		}
	}

	public Province getById(String ac) {
		Optional<Province> p = repo.findById(ac);
		if (!p.isEmpty()) {
			return p.get();
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	public Province insert(Province p) {
		if (repo.findByName(p.getName()) == null) {
			p.setName(Util.formatString(p.getName()));
			p.setAcronym(Util.formatString(p.getAcronym()));
			return repo.save(p);
		} else {
			throw new DatabaseException("Cannot insert new province, there is already one with same name");
		}
	}

	public Province update(Province p) {
		Optional<Province> optP = repo.findById(p.getAcronym());
		if (!optP.isEmpty()) {
			p.setName(Util.formatString(p.getName()));
			p.setAcronym(Util.formatString(p.getAcronym()));
			return repo.save(p);
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	public void delete(Province p) {
		Optional<Province> optP = repo.findById(p.getAcronym());
		if (!optP.isEmpty()) {
			repo.delete(p);
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	public List<Province> GetByNameLike(String name) {
		List<Province> p = repo.findByNameLike("%" + name + "%");
		if (!p.isEmpty()) {
			return p;
		} else {
			throw new ResourceNotFoundException("The province name you are searching could not be found");
		}
	}

	public Province convertDtoToEntity(ProvinceDto p) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		Province prov = new Province();

		Region reg = new Region();
		reg.setId(p.getRegionId());

		prov = modelMapper.map(p, Province.class);
		prov.setRegion(reg);
		return prov;

	}
	
	public Iterable<ProvinceDto> convertEntityToDto(Iterable <Province> listp) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ProvinceDto ProvinceDto = new ProvinceDto();
        List<ProvinceDto> listDto = new ArrayList<ProvinceDto>();
        for(Province p: listp) {
            ProvinceDto = modelMapper.map(p, ProvinceDto.class);  
           listDto.add(ProvinceDto);
        }
        return listDto;
    }
	
	
	public ProvinceDto convertEntityToDto(Province p) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		ProvinceDto rdto = new ProvinceDto();
		rdto = modelMapper.map(p, ProvinceDto.class);
		return rdto;
	}

}
