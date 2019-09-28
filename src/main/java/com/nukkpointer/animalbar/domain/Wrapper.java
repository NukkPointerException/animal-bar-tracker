package com.nukkpointer.animalbar.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.nukkpointer.animalbar.domain.enumeration.WrapperPicture;

/**
 * A Wrapper.
 */
@Entity
@Table(name = "wrapper")
public class Wrapper implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "picture", nullable = false)
    private WrapperPicture picture;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WrapperPicture getPicture() {
        return picture;
    }

    public Wrapper picture(WrapperPicture picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(WrapperPicture picture) {
        this.picture = picture;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wrapper)) {
            return false;
        }
        return id != null && id.equals(((Wrapper) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Wrapper{" +
            "id=" + getId() +
            ", picture='" + getPicture() + "'" +
            "}";
    }
}
