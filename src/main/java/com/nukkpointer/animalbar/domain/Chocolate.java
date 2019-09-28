package com.nukkpointer.animalbar.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.nukkpointer.animalbar.domain.enumeration.ChocolatePicture;

/**
 * A Chocolate.
 */
@Entity
@Table(name = "chocolate")
public class Chocolate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "left_image", nullable = false)
    private ChocolatePicture leftImage;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "right_image", nullable = false)
    private ChocolatePicture rightImage;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChocolatePicture getLeftImage() {
        return leftImage;
    }

    public Chocolate leftImage(ChocolatePicture leftImage) {
        this.leftImage = leftImage;
        return this;
    }

    public void setLeftImage(ChocolatePicture leftImage) {
        this.leftImage = leftImage;
    }

    public ChocolatePicture getRightImage() {
        return rightImage;
    }

    public Chocolate rightImage(ChocolatePicture rightImage) {
        this.rightImage = rightImage;
        return this;
    }

    public void setRightImage(ChocolatePicture rightImage) {
        this.rightImage = rightImage;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chocolate)) {
            return false;
        }
        return id != null && id.equals(((Chocolate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Chocolate{" +
            "id=" + getId() +
            ", leftImage='" + getLeftImage() + "'" +
            ", rightImage='" + getRightImage() + "'" +
            "}";
    }
}
