package it.ecommerce.security;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import it.ecommerce.model.User;
import it.ecommerce.repository.UserRepository;

@RestController
public class LoginController {

	@Autowired
	private UserRepository repo;
	
	
	@RequestMapping("/user")
	public User getUserDetailsAfterLogin(Principal user) {
		User utente = repo.findByEmail(user.getName());
		if (utente!=null) {
			return null;
		}else {
			return null;
		}
		
	}

}
