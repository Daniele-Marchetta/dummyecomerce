package it.ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.ecommerce.dto.PromotionDto;
import it.ecommerce.exceptions.NoDataFoundException;
import it.ecommerce.exceptions.ResourceNotFoundException;
import it.ecommerce.model.Promotion;
import it.ecommerce.repository.PromotionRepository;

@Service
@Transactional
public class PromotionService {

	@Autowired
	private PromotionRepository repo;

	@Autowired
	private ModelMapper modelMapper;

	public Promotion convertDtoToEntity(PromotionDto p) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE).setAmbiguityIgnored(true);
		Promotion prom = new Promotion();
		if(p.getUserId()==null) {
			p.setDiscountType(false);
		}else {
			p.setDiscountType(true);
		}
		prom = modelMapper.map(p, Promotion.class);
		return prom;

	}

//	public Promotion insertDto(PromotionDto p) {
//		Promotion prom = convertDtoToEntity(p);
//		prom.setInsertionDate(LocalDateTime.now());
//		prom.setLastModified(prom.getInsertionDate());
//		return repo.save(prom);
//	}

	public Iterable<Promotion> findAll() {
		Iterable<Promotion> list = repo.findAll();
		if (list.iterator().hasNext()) {
			return repo.findAll();
		} else {
			throw new NoDataFoundException();
		}
	}

	public Promotion getById(Integer id) {
		Optional<Promotion> p = repo.findById(id);
		if (!p.isEmpty()) {
			return p.get();
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	public Promotion insert(Promotion p) {

		p.setInsertionDate(LocalDateTime.now());
		p.setLastModified(p.getInsertionDate());

		return repo.save(p);
	}

	public Promotion update(Promotion p) {
		Optional<Promotion> optP = repo.findById(p.getId());
		if (!optP.isEmpty()) {
			Promotion backup = repo.findById(p.getId()).get();
			p.setInsertionDate(backup.getInsertionDate());
			p.setLastModified(LocalDateTime.now());
			return repo.save(p);
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

//	public Promotion updateDto(PromotionDto p) {
//
//		Promotion prom = convertDtoToEntity(p);
//		Promotion backup = new Promotion();
//		backup = repo.findById(prom.getId()).get();
//		prom.setInsertionDate(backup.getInsertionDate());
//		prom.setLastModified(LocalDateTime.now());
//
//		return repo.save(prom);
//	}

	public void delete(Promotion p) {
		Optional<Promotion> optP = repo.findById(p.getId());
		if (!optP.isEmpty()) {
			repo.delete(p);
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}
	
	
	public Iterable<PromotionDto> convertEntityToDto(Iterable <Promotion> listp) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        PromotionDto PromotionDto = new PromotionDto();
        List<PromotionDto> listDto = new ArrayList<PromotionDto>();
        for(Promotion p: listp) {
            PromotionDto = modelMapper.map(p, PromotionDto.class);  
           listDto.add(PromotionDto);
        }
        return listDto;
    }
	
	
	public PromotionDto convertEntityToDto(Promotion p) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		PromotionDto rdto = new PromotionDto();
		rdto = modelMapper.map(p, PromotionDto.class);
		return rdto;
	}
}
