package com.teste.animaladoption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaAuditing
@EntityScan(basePackages = {"com.teste.animaladoption.entities"})
@EnableJpaRepositories(basePackages = {"com.teste.animaladoption.repositories"})
public class AnimalAdoptionApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnimalAdoptionApplication.class, args);
    }
}
