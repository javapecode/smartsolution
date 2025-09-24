package com.smartsolution;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.smartsolution.model.Role;
import com.smartsolution.repository.RoleRepository;

@SpringBootApplication
public class SmartsolutionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartsolutionApplication.class, args);
	}
//	@Bean
//	public CommandLineRunner setupRoles(RoleRepository roleRepo) {
//	    return args -> {
//	        if (roleRepo.findByName("ADMIN").isEmpty()) {
//	            roleRepo.save(new Role(null, "ADMIN"));
//	        }
//	        if (roleRepo.findByName("USER").isEmpty()) {
//	            roleRepo.save(new Role(null, "USER"));
//	        }
//	    };
//	}

}
