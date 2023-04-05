package com.teste.animaladoption.services;

import com.teste.animaladoption.entities.AnimalEntity;
import com.teste.animaladoption.enums.CategoryEnum;
import com.teste.animaladoption.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;


public interface AnimalService {
    void registerAnimals();

    Page<AnimalEntity> retrieveAnimals(String term, CategoryEnum category, StatusEnum status, LocalDate createdAt, Integer page, Integer size, Sort.Direction direction);

    AnimalEntity changeStatusOfAnimal(Long id, StatusEnum status);
}
