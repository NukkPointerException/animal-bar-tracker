package com.nukkpointer.animalbar.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.nukkpointer.animalbar.domain.enumeration.PuzzleType;

/**
 * A Puzzle.
 */
@Entity
@Table(name = "puzzle")
public class Puzzle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PuzzleType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PuzzleType getType() {
        return type;
    }

    public Puzzle type(PuzzleType type) {
        this.type = type;
        return this;
    }

    public void setType(PuzzleType type) {
        this.type = type;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Puzzle)) {
            return false;
        }
        return id != null && id.equals(((Puzzle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Puzzle{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
