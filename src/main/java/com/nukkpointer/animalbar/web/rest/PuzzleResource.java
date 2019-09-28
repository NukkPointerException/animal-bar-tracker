package com.nukkpointer.animalbar.web.rest;

import com.nukkpointer.animalbar.service.PuzzleService;
import com.nukkpointer.animalbar.web.rest.errors.BadRequestAlertException;
import com.nukkpointer.animalbar.service.dto.PuzzleDTO;

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
 * REST controller for managing {@link com.nukkpointer.animalbar.domain.Puzzle}.
 */
@RestController
@RequestMapping("/api")
public class PuzzleResource {

    private final Logger log = LoggerFactory.getLogger(PuzzleResource.class);

    private static final String ENTITY_NAME = "puzzle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PuzzleService puzzleService;

    public PuzzleResource(PuzzleService puzzleService) {
        this.puzzleService = puzzleService;
    }

    /**
     * {@code POST  /puzzles} : Create a new puzzle.
     *
     * @param puzzleDTO the puzzleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new puzzleDTO, or with status {@code 400 (Bad Request)} if the puzzle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/puzzles")
    public ResponseEntity<PuzzleDTO> createPuzzle(@Valid @RequestBody PuzzleDTO puzzleDTO) throws URISyntaxException {
        log.debug("REST request to save Puzzle : {}", puzzleDTO);
        if (puzzleDTO.getId() != null) {
            throw new BadRequestAlertException("A new puzzle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PuzzleDTO result = puzzleService.save(puzzleDTO);
        return ResponseEntity.created(new URI("/api/puzzles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /puzzles} : Updates an existing puzzle.
     *
     * @param puzzleDTO the puzzleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated puzzleDTO,
     * or with status {@code 400 (Bad Request)} if the puzzleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the puzzleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/puzzles")
    public ResponseEntity<PuzzleDTO> updatePuzzle(@Valid @RequestBody PuzzleDTO puzzleDTO) throws URISyntaxException {
        log.debug("REST request to update Puzzle : {}", puzzleDTO);
        if (puzzleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PuzzleDTO result = puzzleService.save(puzzleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, puzzleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /puzzles} : get all the puzzles.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of puzzles in body.
     */
    @GetMapping("/puzzles")
    public List<PuzzleDTO> getAllPuzzles() {
        log.debug("REST request to get all Puzzles");
        return puzzleService.findAll();
    }

    /**
     * {@code GET  /puzzles/:id} : get the "id" puzzle.
     *
     * @param id the id of the puzzleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the puzzleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/puzzles/{id}")
    public ResponseEntity<PuzzleDTO> getPuzzle(@PathVariable Long id) {
        log.debug("REST request to get Puzzle : {}", id);
        Optional<PuzzleDTO> puzzleDTO = puzzleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(puzzleDTO);
    }

    /**
     * {@code DELETE  /puzzles/:id} : delete the "id" puzzle.
     *
     * @param id the id of the puzzleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/puzzles/{id}")
    public ResponseEntity<Void> deletePuzzle(@PathVariable Long id) {
        log.debug("REST request to delete Puzzle : {}", id);
        puzzleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
