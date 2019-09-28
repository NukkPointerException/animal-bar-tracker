package com.nukkpointer.animalbar.web.rest;

import com.nukkpointer.animalbar.service.EntryService;
import com.nukkpointer.animalbar.web.rest.errors.BadRequestAlertException;
import com.nukkpointer.animalbar.service.dto.EntryDTO;

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
 * REST controller for managing {@link com.nukkpointer.animalbar.domain.Entry}.
 */
@RestController
@RequestMapping("/api")
public class EntryResource {

    private final Logger log = LoggerFactory.getLogger(EntryResource.class);

    private static final String ENTITY_NAME = "entry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntryService entryService;

    public EntryResource(EntryService entryService) {
        this.entryService = entryService;
    }

    /**
     * {@code POST  /entries} : Create a new entry.
     *
     * @param entryDTO the entryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entryDTO, or with status {@code 400 (Bad Request)} if the entry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entries")
    public ResponseEntity<EntryDTO> createEntry(@RequestBody EntryDTO entryDTO) throws URISyntaxException {
        log.debug("REST request to save Entry : {}", entryDTO);
        if (entryDTO.getId() != null) {
            throw new BadRequestAlertException("A new entry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntryDTO result = entryService.save(entryDTO);
        return ResponseEntity.created(new URI("/api/entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entries} : Updates an existing entry.
     *
     * @param entryDTO the entryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entryDTO,
     * or with status {@code 400 (Bad Request)} if the entryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entries")
    public ResponseEntity<EntryDTO> updateEntry(@RequestBody EntryDTO entryDTO) throws URISyntaxException {
        log.debug("REST request to update Entry : {}", entryDTO);
        if (entryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntryDTO result = entryService.save(entryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entries} : get all the entries.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entries in body.
     */
    @GetMapping("/entries")
    public List<EntryDTO> getAllEntries() {
        log.debug("REST request to get all Entries");
        return entryService.findAll();
    }

    /**
     * {@code GET  /entries/:id} : get the "id" entry.
     *
     * @param id the id of the entryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entries/{id}")
    public ResponseEntity<EntryDTO> getEntry(@PathVariable Long id) {
        log.debug("REST request to get Entry : {}", id);
        Optional<EntryDTO> entryDTO = entryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entryDTO);
    }

    /**
     * {@code DELETE  /entries/:id} : delete the "id" entry.
     *
     * @param id the id of the entryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entries/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        log.debug("REST request to delete Entry : {}", id);
        entryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
