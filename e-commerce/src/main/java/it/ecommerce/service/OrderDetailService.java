package it.ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.ecommerce.dto.OrderDetailDto;
import it.ecommerce.exceptions.DatabaseException;
import it.ecommerce.exceptions.NoDataFoundException;
import it.ecommerce.exceptions.ResourceNotFoundException;
import it.ecommerce.model.OrderDetail;
import it.ecommerce.repository.OrderDetailRepository;

@Service
@Transactional
public class OrderDetailService {

	@Autowired
	private OrderDetailRepository repo;

	@Autowired
	private ModelMapper modelMapper;

	public Iterable<OrderDetail> findAll() {
		Iterable<OrderDetail> od = repo.findAll();
		if (od.iterator().hasNext()) {
			return od;
		} else {
			throw new NoDataFoundException();
		}
	}

	public OrderDetail getById(Integer orderId, Integer productId) {
		OrderDetail od = repo.findByOrderIdAndProductId(orderId, productId);

		if (od != null) {
			return od;
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	public OrderDetail insert(OrderDetail o) {
		o.setInsertionDate(LocalDateTime.now());

		OrderDetail od = repo.findByOrderIdAndProductId(o.getOrder().getId(), o.getProduct().getId());

		if (od != null) {
			throw new DatabaseException("composed key already exist!");
		} else {
			return repo.save(o);
		}

	}

	public OrderDetail update(OrderDetail o) {
		OrderDetail backup = repo.findByOrderIdAndProductId(o.getOrder().getId(), o.getProduct().getId());

		if (backup != null) {
			o.setInsertionDate(backup.getInsertionDate());
			return repo.save(o);
		} else {
			throw new ResourceNotFoundException("Resource not found wrong keys ");
		}

	}

	public void delete(OrderDetail o) {

		OrderDetail orddet = repo.findByOrderIdAndProductId(o.getOrder().getId(), o.getProduct().getId());
		if (orddet != null) {
			repo.deleteByOrderIdAndProductId(o.getOrder().getId(), o.getProduct().getId());
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}

	}

	/**
	 * converte l'entità orderdetail nell'entità orderdetaildto ritorna un oggettto
	 * di tipo orderdetaildto
	 * 
	 * @param c
	 * @return
	 */
	public OrderDetailDto convertEntityToDto(OrderDetail o) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		OrderDetailDto OrderDetailDto = new OrderDetailDto();
		OrderDetailDto = modelMapper.map(o, OrderDetailDto.class);
		return OrderDetailDto;
	}
	
	public Iterable<OrderDetailDto> convertEntityToDto(Iterable<OrderDetail> listc) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		OrderDetailDto OrderDetailDto = new OrderDetailDto();
		List<OrderDetailDto> listDto = new ArrayList<OrderDetailDto>();
		for (OrderDetail o : listc) {
			OrderDetailDto = modelMapper.map(o, OrderDetailDto.class);
			listDto.add(OrderDetailDto);
		}
		return listDto;
	}

	/**
	 * converte l'oggetto di tipo orderdetaildto in orderdetail ritorna un oggetto
	 * di tipo orderdetail
	 * 
	 * @param c
	 * @return
	 */
	public OrderDetail convertDtoToEntity(OrderDetailDto o) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		OrderDetail cat = new OrderDetail();
		cat = modelMapper.map(o, OrderDetail.class);
		return cat;

	}

	/**
	 * converte l'oggetto di tipo orderdetaildto in un oggetto di tipo orderdetail
	 * restituisce l'insert della orderdetail
	 * 
	 * @param cat
	 * @return
	 */
	public OrderDetail insertDto(OrderDetailDto o) {
		OrderDetail orderdetail = convertDtoToEntity(o);
		orderdetail.setInsertionDate(LocalDateTime.now());
		return repo.save(orderdetail);
	}

	/**
	 * converte un oggetto di tipo orderdetailDto in un'altro di tipo orderdetail
	 * restituisce l'update sul db
	 * 
	 * @param cat
	 * @return
	 */
	public OrderDetail updateDto(OrderDetailDto ord) {

		OrderDetail orderdetail = convertDtoToEntity(ord);
		OrderDetail backup = new OrderDetail();

		backup = repo.findByOrderIdAndProductId(ord.getOrderId(), ord.getProductId());
		orderdetail.setInsertionDate(backup.getInsertionDate());

		return repo.save(orderdetail);
	}

	public Iterable<OrderDetail> getAllByOrderId(Integer orderId) {
		return getAllByOrderId(orderId);
	}
}
