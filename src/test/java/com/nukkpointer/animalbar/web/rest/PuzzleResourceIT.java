package com.nukkpointer.animalbar.web.rest;

import com.nukkpointer.animalbar.AnimalBarTrackerApp;
import com.nukkpointer.animalbar.domain.Puzzle;
import com.nukkpointer.animalbar.repository.PuzzleRepository;
import com.nukkpointer.animalbar.service.PuzzleService;
import com.nukkpointer.animalbar.service.dto.PuzzleDTO;
import com.nukkpointer.animalbar.service.mapper.PuzzleMapper;
import com.nukkpointer.animalbar.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.nukkpointer.animalbar.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nukkpointer.animalbar.domain.enumeration.PuzzleType;
/**
 * Integration tests for the {@link PuzzleResource} REST controller.
 */
@SpringBootTest(classes = AnimalBarTrackerApp.class)
public class PuzzleResourceIT {

    private static final PuzzleType DEFAULT_TYPE = PuzzleType.CROSSWORD;
    private static final PuzzleType UPDATED_TYPE = PuzzleType.PARROTS_FLYING;

    @Autowired
    private PuzzleRepository puzzleRepository;

    @Autowired
    private PuzzleMapper puzzleMapper;

    @Autowired
    private PuzzleService puzzleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPuzzleMockMvc;

    private Puzzle puzzle;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PuzzleResource puzzleResource = new PuzzleResource(puzzleService);
        this.restPuzzleMockMvc = MockMvcBuilders.standaloneSetup(puzzleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Puzzle createEntity(EntityManager em) {
        Puzzle puzzle = new Puzzle()
            .type(DEFAULT_TYPE);
        return puzzle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Puzzle createUpdatedEntity(EntityManager em) {
        Puzzle puzzle = new Puzzle()
            .type(UPDATED_TYPE);
        return puzzle;
    }

    @BeforeEach
    public void initTest() {
        puzzle = createEntity(em);
    }

    @Test
    @Transactional
    public void createPuzzle() throws Exception {
        int databaseSizeBeforeCreate = puzzleRepository.findAll().size();

        // Create the Puzzle
        PuzzleDTO puzzleDTO = puzzleMapper.toDto(puzzle);
        restPuzzleMockMvc.perform(post("/api/puzzles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puzzleDTO)))
            .andExpect(status().isCreated());

        // Validate the Puzzle in the database
        List<Puzzle> puzzleList = puzzleRepository.findAll();
        assertThat(puzzleList).hasSize(databaseSizeBeforeCreate + 1);
        Puzzle testPuzzle = puzzleList.get(puzzleList.size() - 1);
        assertThat(testPuzzle.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createPuzzleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = puzzleRepository.findAll().size();

        // Create the Puzzle with an existing ID
        puzzle.setId(1L);
        PuzzleDTO puzzleDTO = puzzleMapper.toDto(puzzle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPuzzleMockMvc.perform(post("/api/puzzles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puzzleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Puzzle in the database
        List<Puzzle> puzzleList = puzzleRepository.findAll();
        assertThat(puzzleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = puzzleRepository.findAll().size();
        // set the field null
        puzzle.setType(null);

        // Create the Puzzle, which fails.
        PuzzleDTO puzzleDTO = puzzleMapper.toDto(puzzle);

        restPuzzleMockMvc.perform(post("/api/puzzles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puzzleDTO)))
            .andExpect(status().isBadRequest());

        List<Puzzle> puzzleList = puzzleRepository.findAll();
        assertThat(puzzleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPuzzles() throws Exception {
        // Initialize the database
        puzzleRepository.saveAndFlush(puzzle);

        // Get all the puzzleList
        restPuzzleMockMvc.perform(get("/api/puzzles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(puzzle.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getPuzzle() throws Exception {
        // Initialize the database
        puzzleRepository.saveAndFlush(puzzle);

        // Get the puzzle
        restPuzzleMockMvc.perform(get("/api/puzzles/{id}", puzzle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(puzzle.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPuzzle() throws Exception {
        // Get the puzzle
        restPuzzleMockMvc.perform(get("/api/puzzles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePuzzle() throws Exception {
        // Initialize the database
        puzzleRepository.saveAndFlush(puzzle);

        int databaseSizeBeforeUpdate = puzzleRepository.findAll().size();

        // Update the puzzle
        Puzzle updatedPuzzle = puzzleRepository.findById(puzzle.getId()).get();
        // Disconnect from session so that the updates on updatedPuzzle are not directly saved in db
        em.detach(updatedPuzzle);
        updatedPuzzle
            .type(UPDATED_TYPE);
        PuzzleDTO puzzleDTO = puzzleMapper.toDto(updatedPuzzle);

        restPuzzleMockMvc.perform(put("/api/puzzles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puzzleDTO)))
            .andExpect(status().isOk());

        // Validate the Puzzle in the database
        List<Puzzle> puzzleList = puzzleRepository.findAll();
        assertThat(puzzleList).hasSize(databaseSizeBeforeUpdate);
        Puzzle testPuzzle = puzzleList.get(puzzleList.size() - 1);
        assertThat(testPuzzle.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPuzzle() throws Exception {
        int databaseSizeBeforeUpdate = puzzleRepository.findAll().size();

        // Create the Puzzle
        PuzzleDTO puzzleDTO = puzzleMapper.toDto(puzzle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPuzzleMockMvc.perform(put("/api/puzzles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puzzleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Puzzle in the database
        List<Puzzle> puzzleList = puzzleRepository.findAll();
        assertThat(puzzleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePuzzle() throws Exception {
        // Initialize the database
        puzzleRepository.saveAndFlush(puzzle);

        int databaseSizeBeforeDelete = puzzleRepository.findAll().size();

        // Delete the puzzle
        restPuzzleMockMvc.perform(delete("/api/puzzles/{id}", puzzle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Puzzle> puzzleList = puzzleRepository.findAll();
        assertThat(puzzleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Puzzle.class);
        Puzzle puzzle1 = new Puzzle();
        puzzle1.setId(1L);
        Puzzle puzzle2 = new Puzzle();
        puzzle2.setId(puzzle1.getId());
        assertThat(puzzle1).isEqualTo(puzzle2);
        puzzle2.setId(2L);
        assertThat(puzzle1).isNotEqualTo(puzzle2);
        puzzle1.setId(null);
        assertThat(puzzle1).isNotEqualTo(puzzle2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PuzzleDTO.class);
        PuzzleDTO puzzleDTO1 = new PuzzleDTO();
        puzzleDTO1.setId(1L);
        PuzzleDTO puzzleDTO2 = new PuzzleDTO();
        assertThat(puzzleDTO1).isNotEqualTo(puzzleDTO2);
        puzzleDTO2.setId(puzzleDTO1.getId());
        assertThat(puzzleDTO1).isEqualTo(puzzleDTO2);
        puzzleDTO2.setId(2L);
        assertThat(puzzleDTO1).isNotEqualTo(puzzleDTO2);
        puzzleDTO1.setId(null);
        assertThat(puzzleDTO1).isNotEqualTo(puzzleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(puzzleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(puzzleMapper.fromId(null)).isNull();
    }
}
