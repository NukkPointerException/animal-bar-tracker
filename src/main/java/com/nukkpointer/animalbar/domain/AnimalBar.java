package com.nukkpointer.animalbar.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A AnimalBar.
 */
@Entity
@Table(name = "animal_bar")
public class AnimalBar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Wrapper wrapper;

    @OneToOne
    @JoinColumn(unique = true)
    private Puzzle puzzle;

    @OneToOne
    @JoinColumn(unique = true)
    private Chocolate chocolate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Wrapper getWrapper() {
        return wrapper;
    }

    public AnimalBar wrapper(Wrapper wrapper) {
        this.wrapper = wrapper;
        return this;
    }

    public void setWrapper(Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public AnimalBar puzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
        return this;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public Chocolate getChocolate() {
        return chocolate;
    }

    public AnimalBar chocolate(Chocolate chocolate) {
        this.chocolate = chocolate;
        return this;
    }

    public void setChocolate(Chocolate chocolate) {
        this.chocolate = chocolate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnimalBar)) {
            return false;
        }
        return id != null && id.equals(((AnimalBar) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AnimalBar{" +
            "id=" + getId() +
            "}";
    }
}
