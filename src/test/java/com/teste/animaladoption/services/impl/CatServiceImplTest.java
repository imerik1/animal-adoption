package com.teste.animaladoption.services.impl;

import com.teste.animaladoption.entities.AnimalEntity;
import com.teste.animaladoption.enums.CategoryEnum;
import com.teste.animaladoption.models.CatModel;
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

class CatServiceImplTest {
    @Mock
    private ExternalProviderService externalProviderService;
    @Mock
    private AnimalRepository animalRepository;

    private String catApiUrl = "http://localhost:8080";

    private CatServiceImpl catService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        catService = new CatServiceImpl(externalProviderService, animalRepository);
        ReflectionTestUtils.setField(catService, "catApiUrl", catApiUrl);
    }

    @Test
    void saveNewCats_noLastSavedAnimal() {
        URI requestUri = URI.create(catApiUrl + "/breeds?limit=10&page=0");
        URI requestImageUri = URI.create(catApiUrl + "/images/12345");
        when(animalRepository.findLastApiIdByCategory(CategoryEnum.CAT)).thenReturn(Optional.empty());
        when(externalProviderService.get(requestUri, new ParameterizedTypeReference<List<CatModel>>() {
        }, null))
                .thenReturn(Collections.singletonList(getSampleCatModel()));
        when(externalProviderService.get(requestImageUri, new ParameterizedTypeReference<ImageModel>() {
        }, null))
                .thenReturn(getSampleCatModel().getImage());

        catService.saveNewCats();

        verify(animalRepository, times(2)).findLastApiIdByCategory(CategoryEnum.CAT);
        verify(animalRepository).save(any(AnimalEntity.class));
        verify(externalProviderService).get(eq(requestUri), any(ParameterizedTypeReference.class), eq(null));
    }

    @Test
    void saveNewCats_lastSavedAnimal() {
        URI requestUri = URI.create(catApiUrl + "/breeds?limit=10&page=1");
        URI requestImageUri = URI.create(catApiUrl + "/images/12345");
        when(animalRepository.findLastApiIdByCategory(CategoryEnum.CAT)).thenReturn(Optional.of(10L));
        when(externalProviderService.get(requestUri, new ParameterizedTypeReference<List<CatModel>>() {
        }, null))
                .thenReturn(Collections.singletonList(getSampleCatModel()));
        when(externalProviderService.get(requestImageUri, new ParameterizedTypeReference<ImageModel>() {
        }, null))
                .thenReturn(getSampleCatModel().getImage());

        catService.saveNewCats();

        verify(animalRepository, times(2)).findLastApiIdByCategory(CategoryEnum.CAT);
        verify(animalRepository).save(any(AnimalEntity.class));
        verify(externalProviderService).get(eq(requestUri), any(ParameterizedTypeReference.class), eq(null));
    }

    private CatModel getSampleCatModel() {
        ImageModel imageModel = new ImageModel();
        imageModel.setUrl("https://sample.com/image.jpg");

        CatModel catModel = new CatModel();
        catModel.setName("Sample Cat");
        catModel.setDescription("A sample description");
        catModel.setReferenceImageId("12345");
        catModel.setImage(imageModel);

        return catModel;
    }
}
