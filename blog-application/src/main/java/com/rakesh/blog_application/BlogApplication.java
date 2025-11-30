package com.rakesh.blog_application;

import com.rakesh.blog_application.entity.Role;
import com.rakesh.blog_application.repository.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Blog Application",
				description = "REST APIs Documentation for Blog Application",
				version = "v1.0",
				contact = @Contact(
						name = "Rakesh Anand",
						email = "rakeshanand4you@gmail.com",
						url = "https://www.linkedin.com/in/rakesh-anand-82230a197/"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App Documentation",
				url = "https://github.com/Rakesh123-cin"
		)
)
public class BlogApplication /*implements CommandLineRunner*/ {
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
		System.out.println("Application Started.......");
	}

	/*@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {

		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		roleRepository.save(adminRole);

		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		roleRepository.save(userRole);
	}*/
}
