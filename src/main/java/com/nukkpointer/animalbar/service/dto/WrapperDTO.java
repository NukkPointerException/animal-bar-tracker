package com.nukkpointer.animalbar.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.nukkpointer.animalbar.domain.enumeration.WrapperPicture;

/**
 * A DTO for the {@link com.nukkpointer.animalbar.domain.Wrapper} entity.
 */
public class WrapperDTO implements Serializable {

    private Long id;

    @NotNull
    private WrapperPicture picture;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WrapperPicture getPicture() {
        return picture;
    }

    public void setPicture(WrapperPicture picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WrapperDTO wrapperDTO = (WrapperDTO) o;
        if (wrapperDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wrapperDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WrapperDTO{" +
            "id=" + getId() +
            ", picture='" + getPicture() + "'" +
            "}";
    }
}
