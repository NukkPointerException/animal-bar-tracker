package com.nukkpointer.animalbar.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Entry.
 */
@Entity
@Table(name = "entry")
public class Entry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "public_id")
    private String publicId;

    @OneToOne
    @JoinColumn(unique = true)
    private AnimalBar bar;

    @ManyToOne
    @JsonIgnoreProperties("entries")
    private AppUser appUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Entry date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getPublicId() {
        return publicId;
    }

    public Entry publicId(String publicId) {
        this.publicId = publicId;
        return this;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public AnimalBar getBar() {
        return bar;
    }

    public Entry bar(AnimalBar animalBar) {
        this.bar = animalBar;
        return this;
    }

    public void setBar(AnimalBar animalBar) {
        this.bar = animalBar;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public Entry appUser(AppUser appUser) {
        this.appUser = appUser;
        return this;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entry)) {
            return false;
        }
        return id != null && id.equals(((Entry) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Entry{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", publicId='" + getPublicId() + "'" +
            "}";
    }
}
