package com.teste.animaladoption.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CatModel {
    private String name;
    private String description;
    @JsonProperty("temperament")
    private String temperament;
    private ImageModel image;
    @JsonProperty("reference_image_id")
    private String referenceImageId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getReferenceImageId() {
        return referenceImageId;
    }

    public void setReferenceImageId(String referenceImageId) {
        this.referenceImageId = referenceImageId;
    }

    public String getDescription() {
        if (description != null) {
            return description;
        }
        return temperament;
    }
}
