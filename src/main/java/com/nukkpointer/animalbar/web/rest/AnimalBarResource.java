package com.nukkpointer.animalbar.web.rest;

import com.nukkpointer.animalbar.service.AnimalBarService;
import com.nukkpointer.animalbar.web.rest.errors.BadRequestAlertException;
import com.nukkpointer.animalbar.service.dto.AnimalBarDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.nukkpointer.animalbar.domain.AnimalBar}.
 */
@RestController
@RequestMapping("/api")
public class AnimalBarResource {

    private final Logger log = LoggerFactory.getLogger(AnimalBarResource.class);

    private static final String ENTITY_NAME = "animalBar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnimalBarService animalBarService;

    public AnimalBarResource(AnimalBarService animalBarService) {
        this.animalBarService = animalBarService;
    }

    /**
     * {@code POST  /animal-bars} : Create a new animalBar.
     *
     * @param animalBarDTO the animalBarDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new animalBarDTO, or with status {@code 400 (Bad Request)} if the animalBar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/animal-bars")
    public ResponseEntity<AnimalBarDTO> createAnimalBar(@RequestBody AnimalBarDTO animalBarDTO) throws URISyntaxException {
        log.debug("REST request to save AnimalBar : {}", animalBarDTO);
        if (animalBarDTO.getId() != null) {
            throw new BadRequestAlertException("A new animalBar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnimalBarDTO result = animalBarService.save(animalBarDTO);
        return ResponseEntity.created(new URI("/api/animal-bars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /animal-bars} : Updates an existing animalBar.
     *
     * @param animalBarDTO the animalBarDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated animalBarDTO,
     * or with status {@code 400 (Bad Request)} if the animalBarDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the animalBarDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/animal-bars")
    public ResponseEntity<AnimalBarDTO> updateAnimalBar(@RequestBody AnimalBarDTO animalBarDTO) throws URISyntaxException {
        log.debug("REST request to update AnimalBar : {}", animalBarDTO);
        if (animalBarDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnimalBarDTO result = animalBarService.save(animalBarDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, animalBarDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /animal-bars} : get all the animalBars.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of animalBars in body.
     */
    @GetMapping("/animal-bars")
    public List<AnimalBarDTO> getAllAnimalBars() {
        log.debug("REST request to get all AnimalBars");
        return animalBarService.findAll();
    }

    /**
     * {@code GET  /animal-bars/:id} : get the "id" animalBar.
     *
     * @param id the id of the animalBarDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the animalBarDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/animal-bars/{id}")
    public ResponseEntity<AnimalBarDTO> getAnimalBar(@PathVariable Long id) {
        log.debug("REST request to get AnimalBar : {}", id);
        Optional<AnimalBarDTO> animalBarDTO = animalBarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(animalBarDTO);
    }

    /**
     * {@code DELETE  /animal-bars/:id} : delete the "id" animalBar.
     *
     * @param id the id of the animalBarDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/animal-bars/{id}")
    public ResponseEntity<Void> deleteAnimalBar(@PathVariable Long id) {
        log.debug("REST request to delete AnimalBar : {}", id);
        animalBarService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
