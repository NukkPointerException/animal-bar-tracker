package com.nukkpointer.animalbar.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.nukkpointer.animalbar.domain.AnimalBar} entity.
 */
public class AnimalBarDTO implements Serializable {

    private Long id;


    private Long wrapperId;

    private Long puzzleId;

    private Long chocolateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWrapperId() {
        return wrapperId;
    }

    public void setWrapperId(Long wrapperId) {
        this.wrapperId = wrapperId;
    }

    public Long getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(Long puzzleId) {
        this.puzzleId = puzzleId;
    }

    public Long getChocolateId() {
        return chocolateId;
    }

    public void setChocolateId(Long chocolateId) {
        this.chocolateId = chocolateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnimalBarDTO animalBarDTO = (AnimalBarDTO) o;
        if (animalBarDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), animalBarDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnimalBarDTO{" +
            "id=" + getId() +
            ", wrapper=" + getWrapperId() +
            ", puzzle=" + getPuzzleId() +
            ", chocolate=" + getChocolateId() +
            "}";
    }
}
