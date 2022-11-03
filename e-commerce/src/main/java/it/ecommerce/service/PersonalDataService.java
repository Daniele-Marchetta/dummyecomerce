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

import it.ecommerce.dto.PersonalDataDto;
import it.ecommerce.exceptions.NoDataFoundException;
import it.ecommerce.exceptions.ResourceNotFoundException;
import it.ecommerce.model.PersonalData;
import it.ecommerce.repository.PersonalDataRepository;
import it.ecommerce.util.Util;

@Service
@Transactional
public class PersonalDataService {

	@Autowired
	private PersonalDataRepository repo;

	@Autowired
	private ModelMapper modelMapper;

	public PersonalData convertDtoToEntity(PersonalDataDto p) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		PersonalData pers = new PersonalData();
		pers = modelMapper.map(p, PersonalData.class);
		return pers;

	}

	public Iterable<PersonalData> findAll() {
		Iterable<PersonalData> list = repo.findAll();
		if (list.iterator().hasNext()) {
			return repo.findAll();
		} else {
			throw new NoDataFoundException();
		}
	}

	public PersonalData getById(Integer id) {
		Optional<PersonalData> p = repo.findById(id);
		if (!p.isEmpty()) {
			return p.get();
		} else {
			throw new ResourceNotFoundException("The personal data you are searching could not be found");
		}
	}

	public PersonalData insert(PersonalData p) {

		p.setInsertionDate(LocalDateTime.now());
		p.setLastModified(p.getInsertionDate());
		p.setName(Util.formatString(p.getName()));
		p.setSurname(Util.formatString(p.getSurname()));
		p.setCity(Util.formatString(p.getCity()));
		return repo.save(p);
	}

//	public PersonalData insertDto(PersonalDataDto p) {
//		PersonalData pers = convertDtoToEntity(p);
//		pers.setInsertionDate(LocalDateTime.now());
//		pers.setLastModified(pers.getInsertionDate());
//		return repo.save(pers);
//	}

	public PersonalData update(PersonalData p) {
		Optional<PersonalData> optP = repo.findById(p.getId());
		if (!optP.isEmpty()) {
			PersonalData backup = repo.findById(p.getId()).get();
			p.setInsertionDate(backup.getInsertionDate());
			p.setLastModified(LocalDateTime.now());
			p.setName(Util.formatString(p.getName()));
			p.setSurname(Util.formatString(p.getSurname()));
			p.setCity(Util.formatString(p.getCity()));
			return repo.save(p);
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

//	public PersonalData updateDto(PersonalDataDto p) {
//
//		PersonalData pers = convertDtoToEntity(p);
//		PersonalData backup = new PersonalData();
//		backup = repo.findById(p.getId()).get();
//		pers.setInsertionDate(backup.getInsertionDate());
//		pers.setLastModified(LocalDateTime.now());
//
//		return repo.save(pers);
//	}

	public void delete(PersonalData p) {
		Optional<PersonalData> optP = repo.findById(p.getId());
		if (!optP.isEmpty()) {
			repo.delete(p);
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}
	
	
	

	public Iterable<PersonalDataDto> convertEntityToDto(Iterable <PersonalData> listp) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        PersonalDataDto PersonalDataDto = new PersonalDataDto();
        List<PersonalDataDto> listDto = new ArrayList<PersonalDataDto>();
        for(PersonalData p: listp) {
            PersonalDataDto = modelMapper.map(p, PersonalDataDto.class);  
           listDto.add(PersonalDataDto);
        }
        return listDto;
    }
	
	
	public PersonalDataDto convertEntityToDto(PersonalData p) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		PersonalDataDto rdto = new PersonalDataDto();
		rdto = modelMapper.map(p, PersonalDataDto.class);
		return rdto;
	

}

}
