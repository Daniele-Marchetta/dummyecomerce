package it.ecommerce;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import it.ecommerce.fileManagement.FileStorageProperties;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "it.ecommerce.repository")
@EntityScan(basePackages = { "it.ecommerce.security", "it.ecommerce.model" })
@ComponentScans({ @ComponentScan("it.ecommerce.controller"), @ComponentScan("it.ecommerce.security"),
		@ComponentScan("it.ecommerce.fileManagement") })
@EnableConfigurationProperties({ FileStorageProperties.class })
public class ECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
