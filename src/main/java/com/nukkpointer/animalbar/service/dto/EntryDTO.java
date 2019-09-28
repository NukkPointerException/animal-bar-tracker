package com.nukkpointer.animalbar.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.nukkpointer.animalbar.domain.Entry} entity.
 */
public class EntryDTO implements Serializable {

    private Long id;

    private ZonedDateTime date;

    private String publicId;


    private Long barId;

    private Long appUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public Long getBarId() {
        return barId;
    }

    public void setBarId(Long animalBarId) {
        this.barId = animalBarId;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntryDTO entryDTO = (EntryDTO) o;
        if (entryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntryDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", publicId='" + getPublicId() + "'" +
            ", bar=" + getBarId() +
            ", appUser=" + getAppUserId() +
            "}";
    }
}
