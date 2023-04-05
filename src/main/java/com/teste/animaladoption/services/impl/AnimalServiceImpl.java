package com.teste.animaladoption.services.impl;

import com.teste.animaladoption.entities.AnimalEntity;
import com.teste.animaladoption.enums.CategoryEnum;
import com.teste.animaladoption.enums.StatusEnum;
import com.teste.animaladoption.repositories.AnimalRepository;
import com.teste.animaladoption.services.AnimalService;
import com.teste.animaladoption.services.CatService;
import com.teste.animaladoption.services.DogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponseException;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class AnimalServiceImpl implements AnimalService {
    private final DogService dogService;
    private final CatService catService;
    private final AnimalRepository animalRepository;

    public AnimalServiceImpl(DogService dogService, CatService catService, AnimalRepository animalRepository) {
        this.dogService = dogService;
        this.catService = catService;
        this.animalRepository = animalRepository;
    }

    @Override
    public void registerAnimals() {
        try {
            CompletableFuture<Void> dogServiceFuture = CompletableFuture.runAsync(dogService::saveNewDogs);
            CompletableFuture<Void> catServiceFuture = CompletableFuture.runAsync(catService::saveNewCats);

            CompletableFuture.allOf(dogServiceFuture, catServiceFuture).join();
        } catch (Exception err) {
            throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, err);
        }
    }

    @Override
    public Page<AnimalEntity> retrieveAnimals(String term, CategoryEnum category, StatusEnum status, LocalDate createdAt, Integer page, Integer size, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "id"));
        return animalRepository.findAnimals(term, category, status, createdAt, pageable);
    }

    @Override
    public AnimalEntity changeStatusOfAnimal(Long id, StatusEnum status) {
        try {
            AnimalEntity animalEntity = animalRepository.findById(id)
                    .orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND, new Error("Animal not found")));
            animalEntity.setStatus(status);
            return animalRepository.save(animalEntity);
        } catch (Exception err) {
            throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, err);
        }
    }
}
