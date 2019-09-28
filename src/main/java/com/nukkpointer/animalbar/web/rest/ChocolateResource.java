package com.nukkpointer.animalbar.web.rest;

import com.nukkpointer.animalbar.service.ChocolateService;
import com.nukkpointer.animalbar.web.rest.errors.BadRequestAlertException;
import com.nukkpointer.animalbar.service.dto.ChocolateDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.nukkpointer.animalbar.domain.Chocolate}.
 */
@RestController
@RequestMapping("/api")
public class ChocolateResource {

    private final Logger log = LoggerFactory.getLogger(ChocolateResource.class);

    private static final String ENTITY_NAME = "chocolate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChocolateService chocolateService;

    public ChocolateResource(ChocolateService chocolateService) {
        this.chocolateService = chocolateService;
    }

    /**
     * {@code POST  /chocolates} : Create a new chocolate.
     *
     * @param chocolateDTO the chocolateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chocolateDTO, or with status {@code 400 (Bad Request)} if the chocolate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chocolates")
    public ResponseEntity<ChocolateDTO> createChocolate(@Valid @RequestBody ChocolateDTO chocolateDTO) throws URISyntaxException {
        log.debug("REST request to save Chocolate : {}", chocolateDTO);
        if (chocolateDTO.getId() != null) {
            throw new BadRequestAlertException("A new chocolate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChocolateDTO result = chocolateService.save(chocolateDTO);
        return ResponseEntity.created(new URI("/api/chocolates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chocolates} : Updates an existing chocolate.
     *
     * @param chocolateDTO the chocolateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chocolateDTO,
     * or with status {@code 400 (Bad Request)} if the chocolateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chocolateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chocolates")
    public ResponseEntity<ChocolateDTO> updateChocolate(@Valid @RequestBody ChocolateDTO chocolateDTO) throws URISyntaxException {
        log.debug("REST request to update Chocolate : {}", chocolateDTO);
        if (chocolateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChocolateDTO result = chocolateService.save(chocolateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chocolateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /chocolates} : get all the chocolates.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chocolates in body.
     */
    @GetMapping("/chocolates")
    public List<ChocolateDTO> getAllChocolates() {
        log.debug("REST request to get all Chocolates");
        return chocolateService.findAll();
    }

    /**
     * {@code GET  /chocolates/:id} : get the "id" chocolate.
     *
     * @param id the id of the chocolateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chocolateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chocolates/{id}")
    public ResponseEntity<ChocolateDTO> getChocolate(@PathVariable Long id) {
        log.debug("REST request to get Chocolate : {}", id);
        Optional<ChocolateDTO> chocolateDTO = chocolateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chocolateDTO);
    }

    /**
     * {@code DELETE  /chocolates/:id} : delete the "id" chocolate.
     *
     * @param id the id of the chocolateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chocolates/{id}")
    public ResponseEntity<Void> deleteChocolate(@PathVariable Long id) {
        log.debug("REST request to delete Chocolate : {}", id);
        chocolateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
