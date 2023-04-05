package com.teste.animaladoption.controllers;

import com.teste.animaladoption.entities.AnimalEntity;
import com.teste.animaladoption.enums.CategoryEnum;
import com.teste.animaladoption.enums.StatusEnum;
import com.teste.animaladoption.models.PatchAnimalModel;
import com.teste.animaladoption.services.AnimalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/animal")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<AnimalEntity> retrieveAnimals(
            @RequestParam(value = "term", required = false) String term,
            @RequestParam(value = "category", required = false) CategoryEnum category,
            @RequestParam(value = "status", required = false) StatusEnum status,
            @RequestParam(value = "createdAt", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdAt,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction
    ) {
        return animalService.retrieveAnimals(term, category, status, createdAt, page, size, direction);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void registerAnimals() {
        animalService.registerAnimals();
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    AnimalEntity changeStatusOfAnimal(@RequestBody() PatchAnimalModel patchAnimalModel) {
        return animalService.changeStatusOfAnimal(patchAnimalModel.getId(), patchAnimalModel.getStatus());
    }
}
