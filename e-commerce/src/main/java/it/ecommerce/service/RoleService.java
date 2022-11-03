package it.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.ecommerce.dto.RoleDto;
import it.ecommerce.exceptions.DatabaseException;
import it.ecommerce.exceptions.NoDataFoundException;
import it.ecommerce.exceptions.ResourceNotFoundException;
import it.ecommerce.model.Role;
import it.ecommerce.repository.RoleRepository;
import it.ecommerce.util.Util;

@Service
@Transactional
public class RoleService {

	@Autowired
	private RoleRepository repo;

	@Autowired
	private ModelMapper modelMapper;

	public Role convertDtoToEntity(RoleDto r) {

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		Role role = new Role();
		role = modelMapper.map(r, Role.class);
		return role;

	}

	public Role getById(Integer id) {
		Optional<Role> r = repo.findById(id);
		if (!r.isEmpty()) {
			return repo.findById(id).get();
		} else {
			throw new ResourceNotFoundException("The Role you are searching could not be found");
		}
	}

	public Iterable<Role> getAll() {
		Iterable<Role> list = repo.findAll();
		if (list.iterator().hasNext()) {
			return repo.findAll();
		} else {
			throw new NoDataFoundException();
		}
	}

	public Role insert(Role r) {
		if (repo.findByRole(r.getRole()) == null) {
			r.setInsertionDate(LocalDateTime.now());
			r.setRole(Util.formatRole(r.getRole()));
			return repo.save(r);
		} else {
			throw new DatabaseException("Cannot insert new role, there is already one with same name");
		}
	}

//	public Role insertDto(RoleDto r) {
//		Role role = convertDtoToEntity(r);
//		role.setInsertionDate(LocalDateTime.now());
//		return repo.save(role);
//	}

	public Role update(Role r) {
		Optional<Role> optR = repo.findById(r.getId());
		if (!optR.isEmpty()) {
			Role backup = repo.findById(r.getId()).get();
			r.setInsertionDate(backup.getInsertionDate());
			r.setRole(Util.formatRole(r.getRole()));
			return repo.save(r);
		} else {
			throw new ResourceNotFoundException("The Role you are searching could not be found");
		}
	}

//	public Role updateDto(RoleDto r) {
//
//		Role role = convertDtoToEntity(r);
//		Role backup = new Role();
//		backup = repo.findById(r.getId()).get();
//		role.setInsertionDate(backup.getInsertionDate());
//		return repo.save(role);
//	}

	public void delete(Role r) {
		Optional<Role> optR = repo.findById(r.getId());
		if (!optR.isEmpty()) {
			repo.delete(r);
		} else {
			throw new ResourceNotFoundException("The Role you are searching could not be found");
		}
	}

	public List<Role> getRoleByNameLike(String name) {
		List<Role> r = repo.findByRoleLike("%" + name + "%");

		if (!r.isEmpty()) {
			return repo.findByRoleLike("%" + name + "%");

		} else {
			throw new ResourceNotFoundException("Role name like " + name + " could not be found");
		}
	}

}
