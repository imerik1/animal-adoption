package com.teste.animaladoption.services.impl;

import com.teste.animaladoption.entities.AnimalEntity;
import com.teste.animaladoption.enums.CategoryEnum;
import com.teste.animaladoption.enums.StatusEnum;
import com.teste.animaladoption.repositories.AnimalRepository;
import com.teste.animaladoption.services.CatService;
import com.teste.animaladoption.services.DogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.ErrorResponseException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class AnimalServiceImplTest {
    private AnimalServiceImpl animalService;

    @Mock
    private DogService dogService;

    @Mock
    private CatService catService;

    @Mock
    private AnimalRepository animalRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        animalService = new AnimalServiceImpl(dogService, catService, animalRepository);
    }

    @Test
    void registerAnimals_Successful() {
        doNothing().when(dogService).saveNewDogs();
        doNothing().when(catService).saveNewCats();

        animalService.registerAnimals();

        verify(dogService, times(1)).saveNewDogs();
        verify(catService, times(1)).saveNewCats();
    }

    @Test
    void registerAnimals_Error() {
        doThrow(new RuntimeException()).when(dogService).saveNewDogs();

        assertThrows(ErrorResponseException.class, () -> animalService.registerAnimals());

        verify(dogService, times(1)).saveNewDogs();
    }

    @Test
    void retrieveAnimals_Successful() {
        String term = "term";
        CategoryEnum category = CategoryEnum.CAT;
        StatusEnum status = StatusEnum.AVAILABLE;
        LocalDate createdAt = LocalDate.now();
        int page = 0;
        int size = 10;
        Sort.Direction direction = Sort.Direction.ASC;

        PageRequest pageable = PageRequest.of(page, size, Sort.by(direction, "id"));

        when(animalRepository.findAnimals(term, category, status, createdAt, pageable))
                .thenReturn(Page.empty());

        Page<AnimalEntity> result = animalService.retrieveAnimals(term, category, status, createdAt, page, size, direction);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testChangeStatusOfAnimal_WithInvalidId_ShouldThrowErrorResponseException() {
        Long id = 1L;
        StatusEnum status = StatusEnum.AVAILABLE;
        given(animalRepository.findById(id)).willReturn(Optional.empty());

        assertThrows(ErrorResponseException.class, () -> animalService.changeStatusOfAnimal(id, status));
    }

    @Test
    void testChangeStatusOfAnimal_WithValidId_ShouldReturnUpdatedAnimal() {
        Long id = 1L;
        AnimalEntity animalEntity = new AnimalEntity();
        animalEntity.setId(id);
        animalEntity.setStatus(StatusEnum.AVAILABLE);
        given(animalRepository.findById(id)).willReturn(Optional.of(animalEntity));
        given(animalRepository.save(any(AnimalEntity.class))).willReturn(animalEntity);

        AnimalEntity updatedAnimal = animalService.changeStatusOfAnimal(id, StatusEnum.ADOPTED);

        assertNotNull(updatedAnimal);
        assertEquals(StatusEnum.ADOPTED, updatedAnimal.getStatus());
        verify(animalRepository).findById(id);
        verify(animalRepository).save(any(AnimalEntity.class));
    }
}
