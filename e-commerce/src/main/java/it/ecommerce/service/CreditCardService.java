package it.ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import it.ecommerce.dto.CreditCardDto;
import it.ecommerce.exceptions.DatabaseException;
import it.ecommerce.exceptions.NoDataFoundException;
import it.ecommerce.exceptions.ResourceNotFoundException;
import it.ecommerce.model.CreditCard;
import it.ecommerce.repository.CreditCardRepository;
import it.ecommerce.repository.UserRepository;

@Service
@Transactional
public class CreditCardService {

	@Autowired
	private CreditCardRepository repo;
	
	@Autowired
	private UserRepository repoUser;

	@Autowired
	private ModelMapper modelMapper;

	public CreditCard convertDtoToEntity(CreditCardDto p) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		CreditCard cred = new CreditCard();
		cred = modelMapper.map(p, CreditCard.class);
		return cred;

	}

	public CreditCard getById(Integer userId, String cardNum) {

		CreditCard card = repo.findByUserIdAndCreditCardNum(userId, cardNum);

		if (card != null) {
			return card;
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	public Iterable<CreditCard> getAll() {
		Iterable<CreditCard> card = repo.findAll();

		if (card.iterator().hasNext()) {
			return card;
		} else {
			throw new NoDataFoundException();
		}
	}

	public CreditCard insert(CreditCard c) {
		c.setInsertionDate(LocalDateTime.now());
		c.setLastModified(c.getInsertionDate());

		if (repo.findByUserIdAndCreditCardNum(c.getUser().getId(), c.getCreditCardNum()) != null) {
			throw new DatabaseException("primary key duplicated !");
		} else {
			if (repo.findByCreditCardNum(c.getCreditCardNum()) == null) {
				return repo.save(c);
			} else {
				throw new DatabaseException("Card Already exist !");
			}

		}

	}

//	public CreditCard insertDto(CreditCardDto p) {
//		CreditCard cred = convertDtoToEntity(p);
//		cred.setInsertionDate(LocalDateTime.now());
//		cred.setLastModified(cred.getInsertionDate());
//		return repo.save(cred);
//	}

	public CreditCard update(CreditCard c) {
		CreditCard backup = new CreditCard();
		backup = repo.findByUserIdAndCreditCardNum(c.getUser().getId(), c.getCreditCardNum());

		if (backup != null) {

			c.setInsertionDate(backup.getInsertionDate());
			c.setLastModified(LocalDateTime.now());
			System.out.print(c.toString());
			return repo.save(c);

		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}

	}

//	public CreditCard updateDto(CreditCardDto p) {
//
//		CreditCard cred = convertDtoToEntity(p);
//		CreditCard backup = new CreditCard();
//		backup = repo.findByUserIdAndCreditCardNum(p.getUserId(), p.getCreditCardNum());
//		cred.setInsertionDate(backup.getInsertionDate());
//		cred.setLastModified(LocalDateTime.now());
//
//		return repo.save(cred);
//	}

	public void delete(CreditCard c) {

		CreditCard card = repo.findByUserIdAndCreditCardNum(c.getUser().getId(), c.getCreditCardNum());

		if (card != null) {
			repo.deleteByUserIdAndCreditCardNum(c.getUser().getId(), c.getCreditCardNum());
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	/**
	 * Restituisce tutte le carte di credito di un utente prendendo come parametro
	 * il suo id
	 * 
	 * @param userId
	 * @return
	 */
	public Iterable<CreditCard> findAllByUserId(Authentication auth) {
		String CustomerMail = (String) auth.getPrincipal();
		Iterable <CreditCard> iter =  repo.findAllByUserId(repoUser.findByEmail(CustomerMail).getId());
		if(iter.iterator().hasNext()) {
			return iter;
		}else {
			throw new ResourceNotFoundException("Credit card for this userId not found !!");
		}
	}
	
	public Iterable<CreditCard> findAllByUserId(Integer UserId) {
		Iterable <CreditCard> iter =  repo.findAllByUserId(UserId);
		if(iter.iterator().hasNext()) {
			return iter;
		}else {
			throw new ResourceNotFoundException("Credit card for this userId not found !!");
		}
	}
	
	
	public Iterable<CreditCardDto> convertEntityToDto(Iterable <CreditCard> listp) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        CreditCardDto CreditCardDto = new CreditCardDto();
        List<CreditCardDto> listDto = new ArrayList<CreditCardDto>();
        for(CreditCard p: listp) {
            CreditCardDto = modelMapper.map(p, CreditCardDto.class);  
           listDto.add(CreditCardDto);
        }
        return listDto;
    }
	
	
	public CreditCardDto convertEntityToDto(CreditCard p) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		CreditCardDto rdto = new CreditCardDto();
		rdto = modelMapper.map(p, CreditCardDto.class);
		return rdto;
	

}
}
