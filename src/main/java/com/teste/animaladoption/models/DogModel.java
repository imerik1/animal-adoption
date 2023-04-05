package com.teste.animaladoption.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DogModel {
    private Long id;
    private String name;
    @JsonProperty("temperament")
    private String temperament;
    private ImageModel image;

    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public ImageModel getImage() {
        return image;
    }

    public void setImage(ImageModel image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        if (description != null) {
            return description;
        }
        return temperament;
    }
}
