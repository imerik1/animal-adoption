package com.teste.animaladoption.repositories;

import com.teste.animaladoption.entities.AnimalEntity;
import com.teste.animaladoption.enums.CategoryEnum;
import com.teste.animaladoption.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<AnimalEntity, Long> {
    @Query("SELECT MAX(a.apiId) FROM AnimalEntity a WHERE a.category = :category")
    Optional<Long> findLastApiIdByCategory(@Param("category") CategoryEnum category);

    @Query(
            "SELECT " +
                    "new AnimalEntity(a.id, a.name, a.description, a.sourceImage, a.category, a.createdAt, a.status) " +
                    "FROM AnimalEntity a " +
                    "WHERE " +
                    "(:term IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :term, '%')) " +
                    "OR " +
                    "LOWER(a.description) LIKE LOWER(CONCAT('%', :term, '%'))) " +
                    "AND (:category IS NULL OR a.category = :category) " +
                    "AND (:status IS NULL OR a.status = :status) " +
                    "AND (CAST(:createdAt AS DATE) IS NULL OR a.createdAt = CAST(:createdAt AS DATE))")
    Page<AnimalEntity> findAnimals(
            @Param("term") String term,
            @Param("category") CategoryEnum category,
            @Param("status") StatusEnum status,
            @Param("createdAt") LocalDate createdAt,
            Pageable pageable);
}
