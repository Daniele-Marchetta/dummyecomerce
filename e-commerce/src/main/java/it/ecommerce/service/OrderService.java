package it.ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import it.ecommerce.dto.OrderDto;
import it.ecommerce.exceptions.DatabaseException;
import it.ecommerce.exceptions.NoDataFoundException;
import it.ecommerce.exceptions.ResourceNotFoundException;
import it.ecommerce.model.Cart;
import it.ecommerce.model.Order;
import it.ecommerce.model.User;
import it.ecommerce.repository.OrderRepository;
import it.ecommerce.repository.UserRepository;
import it.ecommerce.security.SecurityUser;

@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository repo;
	
	@Autowired UserRepository uRepo;

	@Autowired
	private ModelMapper modelMapper;

	public Iterable<Order> findAll() {
		Iterable<Order> ord = repo.findAll();

		if (ord.iterator().hasNext()) {
			return ord;
		} else {
			throw new NoDataFoundException();
		}
	}

	public Order getById(Integer id) {
		Optional<Order> ord = repo.findById(id);

		if (ord.isEmpty()) {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		} else {
			return ord.get();
		}
	}
	
	public Iterable <Order> getAllByUserId() {	
		String userMail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		Iterable<Order> ord = repo.findAllbyUserId(uRepo.findByEmail(userMail).getId());
		if (!ord.iterator().hasNext()) {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		} else {
			return ord;
		}
	}

	public Order insert(Order o) {
		User u = new User();
		
		String userMail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		u.setId(uRepo.findByEmail(userMail).getId());
		o.setInsertionDate(LocalDateTime.now());
		o.setLastModified(o.getInsertionDate());
		o.setOrderDate(o.getInsertionDate());
		o.setUser(u);
		if (repo.findByTrackingCode(o.getTrackingCode()) != null) {
			throw new DatabaseException("Tracking code already used!");
		} else {
			return repo.save(o);
		}
	}

	public Order update(Order o) {
		Order backup = new Order();

		backup = repo.findById(o.getId()).get();
		if (backup != null) {
			if (repo.findByTrackingCode(o.getTrackingCode()) == null) {
				o.setInsertionDate(backup.getInsertionDate());
				o.setOrderDate(backup.getOrderDate());
				o.setLastModified(LocalDateTime.now());
				return repo.save(o);
			} else {
				throw new DatabaseException("tracking code already used !");
			}

		}
		throw new ResourceNotFoundException("The resource you are searching could not be found");

	}

	public void delete(Order o, Authentication auth) {

		Optional<Order> ord = repo.findById(o.getId());
		Collection<? extends GrantedAuthority> authCollection = auth.getAuthorities();
		GrantedAuthority thisAuthority = authCollection.iterator().next();

		if (!ord.isEmpty()) {
			if (thisAuthority.getAuthority().equals("ROLE_ADMIN")) {
				repo.delete(o);
			} else {
				if (repo.checkDelete(o.getId()) == false) {
					repo.delete(o);
				} else {
					throw new ResourceNotFoundException("Can't delete , your order is already shipped");
				}
			}
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}

	}

	/**
	 * converte l'entità order nell'entità ordegorDto ritorna un oggettto di tipo
	 * orderto
	 * 
	 * @param c
	 * @return
	 */
	public OrderDto convertEntityToDto(Order o) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		OrderDto OrderDto = new OrderDto();
		OrderDto = modelMapper.map(o, OrderDto.class);
		return OrderDto;
	}

	/**
	 * converte l'oggetto di tipo ordegorDto in ordegor ritorna un oggetto di tipo
	 * order
	 * 
	 * @param c
	 * @return
	 */
	public Order convertDtoToEntity(OrderDto o) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		Order order = new Order();
		order = modelMapper.map(o, Order.class);
		return order;

	}

	/**
	 * converte l'oggetto di tipo orderto in un oggetto di tipo order restituisce
	 * l'insert della order
	 * 
	 * @param ord
	 * @return
	 */
	public Order insertDto(OrderDto ord) {
		Order order = convertDtoToEntity(ord);
		order.setInsertionDate(LocalDateTime.now());
		order.setLastModified(order.getInsertionDate());
		order.setInsertionDate(order.getInsertionDate());
		return repo.save(order);
	}

	/**
	 * converte un oggetto di tipo orderDto in un'altro di tipo order restituisce
	 * l'update sul db
	 * 
	 * @param ord
	 * @return
	 */
	public Order updateDto(OrderDto ord) {

		Order order = convertDtoToEntity(ord);
		Order backup = new Order();

		backup = repo.findById(order.getId()).get();
		order.setInsertionDate(backup.getInsertionDate());
		order.setLastModified(LocalDateTime.now());
		order.setOrderDate(backup.getOrderDate());
		return repo.save(order);
	}

	public Iterable<OrderDto> convertEntityToDto(Iterable<Order> listp) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		OrderDto OrderDto = new OrderDto();
		List<OrderDto> listDto = new ArrayList<OrderDto>();
		for (Order p : listp) {
			OrderDto = modelMapper.map(p, OrderDto.class);
			listDto.add(OrderDto);
		}
		return listDto;
	}

}
