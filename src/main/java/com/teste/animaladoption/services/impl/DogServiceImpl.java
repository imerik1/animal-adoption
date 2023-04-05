package com.teste.animaladoption.services.impl;

import com.teste.animaladoption.entities.AnimalEntity;
import com.teste.animaladoption.enums.CategoryEnum;
import com.teste.animaladoption.enums.StatusEnum;
import com.teste.animaladoption.models.DogModel;
import com.teste.animaladoption.repositories.AnimalRepository;
import com.teste.animaladoption.services.DogService;
import com.teste.animaladoption.services.ExternalProviderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DogServiceImpl implements DogService {
    @Value("${dog_api.url}")
    private String dogApiUrl;
    private final AnimalRepository animalRepository;
    private final ExternalProviderService externalProviderService;

    public DogServiceImpl(ExternalProviderService externalProviderService, AnimalRepository animalRepository) {
        this.externalProviderService = externalProviderService;
        this.animalRepository = animalRepository;
    }

    @Override
    public void saveNewDogs() {
        Long lastAnimalSaved = animalRepository.findLastApiIdByCategory(CategoryEnum.DOG).orElse(0L);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("limit", "10");
        queryParams.put("page", String.format("%d", lastAnimalSaved / 10));

        URI requestUri = ExternalProviderService.builderUri(dogApiUrl, queryParams, "breeds");

        externalProviderService.<List<DogModel>>get(requestUri, new ParameterizedTypeReference<List<DogModel>>() {
                }, null)
                .forEach((dogModel) -> {
                    AnimalEntity animalEntity = new AnimalEntity();
                    animalEntity.setApiId(dogModel.getId());
                    animalEntity.setSourceImage(dogModel.getImage().getUrl());
                    animalEntity.setName(dogModel.getName());
                    animalEntity.setDescription(dogModel.getDescription());
                    animalEntity.setStatus(StatusEnum.AVAILABLE);
                    animalEntity.setCategory(CategoryEnum.DOG);
                    animalRepository.save(animalEntity);
                });
    }
}
