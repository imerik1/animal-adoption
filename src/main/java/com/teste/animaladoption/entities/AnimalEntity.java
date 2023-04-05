package com.teste.animaladoption.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teste.animaladoption.enums.CategoryEnum;
import com.teste.animaladoption.enums.StatusEnum;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "animal")
public class AnimalEntity extends BaseEntity {
    private String name;
    @Column(length = 1200)
    private String description;
    private String sourceImage;
    @Enumerated(EnumType.ORDINAL)
    private CategoryEnum category;
    @Enumerated(EnumType.ORDINAL)
    private StatusEnum status;
    @JsonIgnore
    private Long apiId;

    public AnimalEntity() {
    }

    public AnimalEntity(Long id, String name, String description, String sourceImage, CategoryEnum category, LocalDate createdAt, StatusEnum status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sourceImage = sourceImage;
        this.category = category;
        this.createdAt = createdAt;
        this.status = status;
    }

    public Long getApiId() {
        return apiId;
    }

    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourceImage() {
        return sourceImage;
    }

    public void setSourceImage(String sourceImage) {
        this.sourceImage = sourceImage;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
