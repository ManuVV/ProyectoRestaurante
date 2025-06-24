package com.restaurante.backend.config;

import com.restaurante.backend.model.RolEntity;
import com.restaurante.backend.repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

	/*
	 * @Bean public CommandLineRunner initRoles(RolRepository rolRepository) {
	 * return args -> { if (rolRepository.findByNombre("USER").isEmpty()) {
	 * rolRepository.save(new RolEntity("USER"));
	 * System.out.println("Rol USER creado"); }
	 * 
	 * if (rolRepository.findByNombre("ADMIN").isEmpty()) { rolRepository.save(new
	 * RolEntity("ADMIN")); System.out.println("Rol ADMIN creado"); } }; }
	 */
}