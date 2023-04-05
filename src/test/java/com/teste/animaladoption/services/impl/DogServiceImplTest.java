package com.teste.animaladoption.services.impl;

import com.teste.animaladoption.entities.AnimalEntity;
import com.teste.animaladoption.enums.CategoryEnum;
import com.teste.animaladoption.models.DogModel;
import com.teste.animaladoption.models.ImageModel;
import com.teste.animaladoption.repositories.AnimalRepository;
import com.teste.animaladoption.services.ExternalProviderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DogServiceImplTest {
    @Mock
    private ExternalProviderService externalProviderService;
    @Mock
    private AnimalRepository animalRepository;

    private String dogApiUrl = "http://localhost:8080";

    private DogServiceImpl dogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        dogService = new DogServiceImpl(externalProviderService, animalRepository);
        ReflectionTestUtils.setField(dogService, "dogApiUrl", dogApiUrl);
    }

    @Test
    void saveNewDogs_noLastSavedAnimal() {
        URI requestUri = URI.create(dogApiUrl + "/breeds?limit=10&page=0");
        when(animalRepository.findLastApiIdByCategory(CategoryEnum.DOG)).thenReturn(Optional.empty());
        when(externalProviderService.get(requestUri, new ParameterizedTypeReference<List<DogModel>>() {
        }, null))
                .thenReturn(Collections.singletonList(getSampleDogModel()));

        dogService.saveNewDogs();

        verify(animalRepository).findLastApiIdByCategory(CategoryEnum.DOG);
        verify(animalRepository).save(any(AnimalEntity.class));
        verify(externalProviderService).get(eq(requestUri), any(ParameterizedTypeReference.class), eq(null));
    }

    @Test
    void saveNewDogs_lastSavedAnimal() {
        URI requestUri = URI.create(dogApiUrl + "/breeds?limit=10&page=1");
        when(animalRepository.findLastApiIdByCategory(CategoryEnum.DOG)).thenReturn(Optional.of(10L));
        when(externalProviderService.get(requestUri, new ParameterizedTypeReference<List<DogModel>>() {
        }, null))
                .thenReturn(Collections.singletonList(getSampleDogModel()));

        dogService.saveNewDogs();

        verify(animalRepository).findLastApiIdByCategory(CategoryEnum.DOG);
        verify(animalRepository).save(any(AnimalEntity.class));
        verify(externalProviderService).get(eq(requestUri), any(ParameterizedTypeReference.class), eq(null));
    }

    private DogModel getSampleDogModel() {
        ImageModel imageModel = new ImageModel();
        imageModel.setUrl("https://sample.com/image.jpg");

        DogModel dogModel = new DogModel();
        dogModel.setName("Sample Dog");
        dogModel.setDescription("A sample description");
        dogModel.setImage(imageModel);

        return dogModel;
    }
}
