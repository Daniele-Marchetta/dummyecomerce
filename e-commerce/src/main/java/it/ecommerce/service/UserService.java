package it.ecommerce.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.ecommerce.dto.UserDto;
import it.ecommerce.exceptions.DatabaseException;
import it.ecommerce.exceptions.NoDataFoundException;
import it.ecommerce.exceptions.ResourceNotFoundException;
import it.ecommerce.model.Cart;
import it.ecommerce.model.Role;
import it.ecommerce.model.User;
import it.ecommerce.repository.CartProductRepository;
import it.ecommerce.repository.CartRepository;
import it.ecommerce.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private CartRepository repoCart;

	@Autowired
	private CartProductService repoCartProductService;

	@Autowired
	private ModelMapper modelMapper;

	private PasswordEncoder passwordEncoder;

	public UserService(PasswordEncoder passwordEncoder) {
		super();
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	public User convertDtoToEntity(UserDto u) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		User us = new User();
		us = modelMapper.map(u, User.class);
		return us;

	}

	public User getById(Integer id) {
		Optional<User> u = repo.findById(id);
		if (!u.isEmpty()) {
			return repo.findById(id).get();
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	public Iterable<User> getAll() {
		Iterable<User> list = repo.findAll();
		if (list.iterator().hasNext()) {
			return repo.findAll();
		} else {
			throw new NoDataFoundException();
		}
	}

	public User insert(User u) {
		if (repo.findByEmail(u.getEmail()) == null && repo.findByPersonalDataId(u.getPersonalData().getId()) == null) {

			Role role = new Role();
			role.setId(1);

			u.setInsertionDate(LocalDateTime.now());
			u.setLastModified(u.getInsertionDate());
			String encodedPassword = this.passwordEncoder.encode(u.getHashedPassword());
			u.setHashedPassword(encodedPassword);
			u.setRole(role);

			repo.save(u);
			saveCart(u.getEmail());
			return u;
		} else {
			throw new DatabaseException("Cannot insert new user, there is already one with same email or personal id");
		}
	}

	private void saveCart(String mail) {
		User u = repo.findByEmail(mail);
		Cart c = new Cart();
		c.setUserId(u.getId());
		c.setInsertionDate(LocalDateTime.now());
		repoCart.save(c);
	}

//	public User insertDto(UserDto u) {
//		User us = convertDtoToEntity(u);
//		us.setInsertionDate(LocalDateTime.now());
//		us.setLastModified(us.getInsertionDate());
//		return repo.save(us);
//	}

	public User update(User u) {
		Optional<User> optU = repo.findById(u.getId());
		if (!optU.isEmpty()) {
			User backup = repo.findById(u.getId()).get();
			u.setInsertionDate(backup.getInsertionDate());
			u.setLastModified(LocalDateTime.now());
			return repo.save(u);
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

//	public User updateDto(UserDto u) {
//
//		User us = convertDtoToEntity(u);
//		User backup = new User();
//		backup = repo.findById(u.getId()).get();
//		us.setInsertionDate(backup.getInsertionDate());
//		us.setLastModified(LocalDateTime.now());
//
//		return repo.save(us);
//	}

	public void delete(User u) {
		Optional<User> optU = repo.findById(u.getId());
		if (!optU.isEmpty()) {
			repoCartProductService.emptyCart(u.getId());
			repoCart.delete(repoCart.findByUserId(u.getId()));
			repo.delete(u);
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	public Iterable<User> getUsersByRoleId(Integer roleId) {
		Iterable<User> list = repo.findAllByRoleId(roleId);
		if (list.iterator().hasNext()) {
			return repo.findAllByRoleId(roleId);
		} else {
			throw new NoDataFoundException();
		}
	}

	public User getByEmail(String email) {
		User u = repo.findByEmail(email);
		if (!(u == null)) {
			return repo.findByEmail(email);
		} else {
			throw new ResourceNotFoundException("The resource you are searching could not be found");
		}
	}

	public void checkEmail(User u) {
		repo.save(u);
		System.out.println("ho salvato");
		if (!u.getEmail().contains("@")) {
			throw new RuntimeException("L'email non contiene una chiocciola, verrà effettuato il rollback!");
		}
	}

	public User insertCheckingEmail(User u) {
		u.setInsertionDate(LocalDateTime.now());
		u.setLastModified(u.getInsertionDate());
		try {
			checkEmail(u);
		} catch (RuntimeErrorException e) {
			System.out.println(e.getMessage());
		}
		return u;
	}

	public Iterable<UserDto> convertEntityToDto(Iterable<User> listp) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		UserDto UserDto = new UserDto();
		List<UserDto> listDto = new ArrayList<UserDto>();
		for (User p : listp) {
			UserDto = modelMapper.map(p, UserDto.class);
			listDto.add(UserDto);
		}
		return listDto;
	}

	public UserDto convertEntityToDto(User p) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		UserDto rdto = new UserDto();
		rdto = modelMapper.map(p, UserDto.class);
		return rdto;
	}

	public void changePassword(String email, String oldpass, String newpass) {

		User u = repo.findByEmail(email);

		if (u != null) {
			if (passwordEncoder.matches(oldpass, u.getHashedPassword())) {
				String encodedPass = passwordEncoder.encode(newpass);
				u.setHashedPassword(encodedPass);
			} else {
				throw new DatabaseException("old password doesn't match !!");
			}
		} else {
			throw new ResourceNotFoundException("User email " + email + " not found !!");
		}

	}

}
