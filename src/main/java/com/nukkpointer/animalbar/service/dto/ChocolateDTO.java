package com.nukkpointer.animalbar.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.nukkpointer.animalbar.domain.enumeration.ChocolatePicture;

/**
 * A DTO for the {@link com.nukkpointer.animalbar.domain.Chocolate} entity.
 */
public class ChocolateDTO implements Serializable {

    private Long id;

    @NotNull
    private ChocolatePicture leftImage;

    @NotNull
    private ChocolatePicture rightImage;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChocolatePicture getLeftImage() {
        return leftImage;
    }

    public void setLeftImage(ChocolatePicture leftImage) {
        this.leftImage = leftImage;
    }

    public ChocolatePicture getRightImage() {
        return rightImage;
    }

    public void setRightImage(ChocolatePicture rightImage) {
        this.rightImage = rightImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChocolateDTO chocolateDTO = (ChocolateDTO) o;
        if (chocolateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chocolateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChocolateDTO{" +
            "id=" + getId() +
            ", leftImage='" + getLeftImage() + "'" +
            ", rightImage='" + getRightImage() + "'" +
            "}";
    }
}
