package it.ecommerce;


import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.ecommerce.exceptions.DatabaseException;
import it.ecommerce.model.PersonalData;
import it.ecommerce.model.User;
import it.ecommerce.repository.CartRepository;
import it.ecommerce.repository.UserRepository;
import it.ecommerce.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserInsertTest {

	@InjectMocks
	UserService userServiceMock;
	@Mock
	UserRepository userRepoMock;
	@Mock
	ModelMapper modelMapper;
	@Mock
	PasswordEncoder passwordEncoder;
	@Mock
	CartRepository repoCart;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expected = DatabaseException.class)
	public void testUserInsertExceptionEmail() {

		// utente esistente nel db
		User user = new User();
		user.setEmail("customer2@gmail.com");

		Mockito.when(userRepoMock.findByEmail(user.getEmail())).thenReturn(user);

		// viene lanciata un'exception perchè l'utente con quell'email esiste già
		userServiceMock.insert(user);

	}

	@Test(expected = DatabaseException.class)
	public void testUserInsertExceptionPersonalData() {

		// utente con email non esistente ma con personal data esistente nel db
		PersonalData p = new PersonalData();
		p.setId(10);

		User user = new User();
		user.setPersonalData(p);
		user.setEmail("emailnonesistente@gmail.com");

		// find by mail non trova un utente con quella mail (NULL) mentre
		// findbypersonaldataid trova un personal data associato
		// all'utente user
		Mockito.when(userRepoMock.findByEmail(user.getEmail())).thenReturn(null);
		Mockito.when(userRepoMock.findByPersonalDataId(user.getPersonalData().getId())).thenReturn(user);
		

		// viene lanciata un'exception perchè eiste già nel db un utente con quel
		// personal data associato
		userServiceMock.insert(user);

	}

	@Test
	public void testUserInsertSuccessful() {

		PersonalData p = new PersonalData();
		p.setId(10);

		User user = spy(User.class);
		user.setEmail("customer99@gmail.com");
		user.setHashedPassword("prova");
		user.setPersonalData(p);

		Mockito.when(userRepoMock.findByEmail("customer99@gmail.com")).thenReturn(null).thenReturn(user);
		Mockito.when(userRepoMock.findByPersonalDataId(10)).thenReturn(null);
		

		userServiceMock.insert(user);
		
		verify(passwordEncoder).encode("prova");
		verify(userRepoMock).save(user);
		verify(userRepoMock,times(2)).findByEmail("customer99@gmail.com");
	}

}
