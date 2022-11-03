package it.ecommerce.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.ecommerce.model.User;
import it.ecommerce.repository.UserRepository;

@Service
public class EcommerceUserDetails implements UserDetailsService {
	
	
	@Autowired
	private UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User utente = repo.findByEmail(username);
		if (utente == null) {
			throw new UsernameNotFoundException("User details not found for the user : " + username);
		}
		return new SecurityUser(utente);
	}

}
