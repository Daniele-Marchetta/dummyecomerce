package it.ecommerce.security;




import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import it.ecommerce.security.filter.JWTTokenGeneratorFilter;
import it.ecommerce.security.filter.JWTTokenValidatorFilter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,  jsr250Enabled = true)
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter{

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().csrf().disable().cors().and()
//		.addFilterBefore(new RequestValidationBeforeFilter(),BasicAuthenticationFilter.class)
//		.addFilterAfter(new AuthoritiesLoggingAfterFilter(),BasicAuthenticationFilter.class)
//		.addFilterAt(new AuthoritiesLoggingAtFilter(),BasicAuthenticationFilter.class)
		.addFilterBefore(new JWTTokenValidatorFilter(),BasicAuthenticationFilter.class)
		.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
		.authorizeRequests()
		.mvcMatchers("/carts").hasRole("ADMIN")
		.mvcMatchers("/roles").hasRole("ADMIN")
		.mvcMatchers("/user").authenticated()
		.mvcMatchers("/users").permitAll()
		.mvcMatchers(HttpMethod.POST,"/personal-data").permitAll()
		.mvcMatchers(HttpMethod.GET,"/provincies").permitAll()
		.mvcMatchers("/**").authenticated()
		// alla creazione dell'utente come impedire che l'utente si metta il ruolo da solo
		//.mvcMatchers("/personal-data").anonymous()
		.and().formLogin().and().httpBasic();
	}
	
	


    // USO BCRYPT PER CRIPTARE
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
//	
//	@Bean 
//	public UserDetailsService userDetailsService(DataSource dataSource) {
//		  return new JdbcUserDetailsManager(dataSource); 
//		  }
//	
	
}
