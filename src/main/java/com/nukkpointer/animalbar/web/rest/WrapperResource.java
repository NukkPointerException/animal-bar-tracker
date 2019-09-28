package com.nukkpointer.animalbar.web.rest;

import com.nukkpointer.animalbar.service.WrapperService;
import com.nukkpointer.animalbar.web.rest.errors.BadRequestAlertException;
import com.nukkpointer.animalbar.service.dto.WrapperDTO;

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
 * REST controller for managing {@link com.nukkpointer.animalbar.domain.Wrapper}.
 */
@RestController
@RequestMapping("/api")
public class WrapperResource {

    private final Logger log = LoggerFactory.getLogger(WrapperResource.class);

    private static final String ENTITY_NAME = "wrapper";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WrapperService wrapperService;

    public WrapperResource(WrapperService wrapperService) {
        this.wrapperService = wrapperService;
    }

    /**
     * {@code POST  /wrappers} : Create a new wrapper.
     *
     * @param wrapperDTO the wrapperDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wrapperDTO, or with status {@code 400 (Bad Request)} if the wrapper has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wrappers")
    public ResponseEntity<WrapperDTO> createWrapper(@Valid @RequestBody WrapperDTO wrapperDTO) throws URISyntaxException {
        log.debug("REST request to save Wrapper : {}", wrapperDTO);
        if (wrapperDTO.getId() != null) {
            throw new BadRequestAlertException("A new wrapper cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WrapperDTO result = wrapperService.save(wrapperDTO);
        return ResponseEntity.created(new URI("/api/wrappers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wrappers} : Updates an existing wrapper.
     *
     * @param wrapperDTO the wrapperDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wrapperDTO,
     * or with status {@code 400 (Bad Request)} if the wrapperDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wrapperDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wrappers")
    public ResponseEntity<WrapperDTO> updateWrapper(@Valid @RequestBody WrapperDTO wrapperDTO) throws URISyntaxException {
        log.debug("REST request to update Wrapper : {}", wrapperDTO);
        if (wrapperDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WrapperDTO result = wrapperService.save(wrapperDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, wrapperDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /wrappers} : get all the wrappers.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wrappers in body.
     */
    @GetMapping("/wrappers")
    public List<WrapperDTO> getAllWrappers() {
        log.debug("REST request to get all Wrappers");
        return wrapperService.findAll();
    }

    /**
     * {@code GET  /wrappers/:id} : get the "id" wrapper.
     *
     * @param id the id of the wrapperDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wrapperDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wrappers/{id}")
    public ResponseEntity<WrapperDTO> getWrapper(@PathVariable Long id) {
        log.debug("REST request to get Wrapper : {}", id);
        Optional<WrapperDTO> wrapperDTO = wrapperService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wrapperDTO);
    }

    /**
     * {@code DELETE  /wrappers/:id} : delete the "id" wrapper.
     *
     * @param id the id of the wrapperDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wrappers/{id}")
    public ResponseEntity<Void> deleteWrapper(@PathVariable Long id) {
        log.debug("REST request to delete Wrapper : {}", id);
        wrapperService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
