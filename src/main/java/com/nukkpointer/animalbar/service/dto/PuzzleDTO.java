package com.nukkpointer.animalbar.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.nukkpointer.animalbar.domain.enumeration.PuzzleType;

/**
 * A DTO for the {@link com.nukkpointer.animalbar.domain.Puzzle} entity.
 */
public class PuzzleDTO implements Serializable {

    private Long id;

    @NotNull
    private PuzzleType type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PuzzleType getType() {
        return type;
    }

    public void setType(PuzzleType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PuzzleDTO puzzleDTO = (PuzzleDTO) o;
        if (puzzleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), puzzleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PuzzleDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
