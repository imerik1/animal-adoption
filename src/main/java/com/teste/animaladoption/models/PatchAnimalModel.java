package com.teste.animaladoption.models;

import com.teste.animaladoption.enums.StatusEnum;

public class PatchAnimalModel {
    private Long id;
    private StatusEnum status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
