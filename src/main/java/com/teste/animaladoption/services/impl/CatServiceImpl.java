package com.teste.animaladoption.services.impl;

import com.teste.animaladoption.entities.AnimalEntity;
import com.teste.animaladoption.enums.CategoryEnum;
import com.teste.animaladoption.enums.StatusEnum;
import com.teste.animaladoption.models.CatModel;
import com.teste.animaladoption.models.ImageModel;
import com.teste.animaladoption.repositories.AnimalRepository;
import com.teste.animaladoption.services.CatService;
import com.teste.animaladoption.services.ExternalProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CatServiceImpl implements CatService {
    @Value("${cat_api.url}")
    private String catApiUrl;
    Logger logger = LoggerFactory.getLogger(CatServiceImpl.class);
    private final AnimalRepository animalRepository;
    private final ExternalProviderService externalProviderService;

    public CatServiceImpl(ExternalProviderService externalProviderService, AnimalRepository animalRepository) {
        this.externalProviderService = externalProviderService;
        this.animalRepository = animalRepository;
    }

    @Override
    public void saveNewCats() {
        Long lastAnimalSaved = animalRepository.findLastApiIdByCategory(CategoryEnum.CAT).orElse(0L);
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("limit", "10");
        queryParams.put("page", String.format("%d", lastAnimalSaved / 10));

        URI requestUri = ExternalProviderService.builderUri(catApiUrl, queryParams, "breeds");

        externalProviderService.<List<CatModel>>get(requestUri, new ParameterizedTypeReference<List<CatModel>>() {
                }, null)
                .forEach((catModel) -> {
                    try {
                        AnimalEntity animalEntity = new AnimalEntity();
                        Long id = animalRepository.findLastApiIdByCategory(CategoryEnum.CAT).orElse(0L) + 1L;

                        URI imageRequestUri = ExternalProviderService.builderUri(catApiUrl, null, "images", catModel.getReferenceImageId());
                        ImageModel imageModel = externalProviderService.get(imageRequestUri, new ParameterizedTypeReference<ImageModel>() {
                        }, null);

                        catModel.setImage(imageModel);

                        animalEntity.setApiId(id);
                        animalEntity.setSourceImage(catModel.getImage().getUrl());
                        animalEntity.setName(catModel.getName());
                        animalEntity.setDescription(catModel.getDescription());
                        animalEntity.setStatus(StatusEnum.AVAILABLE);
                        animalEntity.setCategory(CategoryEnum.CAT);
                        animalRepository.save(animalEntity);
                    } catch (Exception ex) {
                        logger.error(ex.getMessage());
                    }
                });
    }
}
