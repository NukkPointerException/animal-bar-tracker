package com.nukkpointer.animalbar.web.rest;

import com.nukkpointer.animalbar.AnimalBarTrackerApp;
import com.nukkpointer.animalbar.domain.AnimalBar;
import com.nukkpointer.animalbar.repository.AnimalBarRepository;
import com.nukkpointer.animalbar.service.AnimalBarService;
import com.nukkpointer.animalbar.service.dto.AnimalBarDTO;
import com.nukkpointer.animalbar.service.mapper.AnimalBarMapper;
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

/**
 * Integration tests for the {@link AnimalBarResource} REST controller.
 */
@SpringBootTest(classes = AnimalBarTrackerApp.class)
public class AnimalBarResourceIT {

    @Autowired
    private AnimalBarRepository animalBarRepository;

    @Autowired
    private AnimalBarMapper animalBarMapper;

    @Autowired
    private AnimalBarService animalBarService;

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

    private MockMvc restAnimalBarMockMvc;

    private AnimalBar animalBar;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnimalBarResource animalBarResource = new AnimalBarResource(animalBarService);
        this.restAnimalBarMockMvc = MockMvcBuilders.standaloneSetup(animalBarResource)
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
    public static AnimalBar createEntity(EntityManager em) {
        AnimalBar animalBar = new AnimalBar();
        return animalBar;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnimalBar createUpdatedEntity(EntityManager em) {
        AnimalBar animalBar = new AnimalBar();
        return animalBar;
    }

    @BeforeEach
    public void initTest() {
        animalBar = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnimalBar() throws Exception {
        int databaseSizeBeforeCreate = animalBarRepository.findAll().size();

        // Create the AnimalBar
        AnimalBarDTO animalBarDTO = animalBarMapper.toDto(animalBar);
        restAnimalBarMockMvc.perform(post("/api/animal-bars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalBarDTO)))
            .andExpect(status().isCreated());

        // Validate the AnimalBar in the database
        List<AnimalBar> animalBarList = animalBarRepository.findAll();
        assertThat(animalBarList).hasSize(databaseSizeBeforeCreate + 1);
        AnimalBar testAnimalBar = animalBarList.get(animalBarList.size() - 1);
    }

    @Test
    @Transactional
    public void createAnimalBarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = animalBarRepository.findAll().size();

        // Create the AnimalBar with an existing ID
        animalBar.setId(1L);
        AnimalBarDTO animalBarDTO = animalBarMapper.toDto(animalBar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnimalBarMockMvc.perform(post("/api/animal-bars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalBarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalBar in the database
        List<AnimalBar> animalBarList = animalBarRepository.findAll();
        assertThat(animalBarList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnimalBars() throws Exception {
        // Initialize the database
        animalBarRepository.saveAndFlush(animalBar);

        // Get all the animalBarList
        restAnimalBarMockMvc.perform(get("/api/animal-bars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animalBar.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getAnimalBar() throws Exception {
        // Initialize the database
        animalBarRepository.saveAndFlush(animalBar);

        // Get the animalBar
        restAnimalBarMockMvc.perform(get("/api/animal-bars/{id}", animalBar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(animalBar.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAnimalBar() throws Exception {
        // Get the animalBar
        restAnimalBarMockMvc.perform(get("/api/animal-bars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimalBar() throws Exception {
        // Initialize the database
        animalBarRepository.saveAndFlush(animalBar);

        int databaseSizeBeforeUpdate = animalBarRepository.findAll().size();

        // Update the animalBar
        AnimalBar updatedAnimalBar = animalBarRepository.findById(animalBar.getId()).get();
        // Disconnect from session so that the updates on updatedAnimalBar are not directly saved in db
        em.detach(updatedAnimalBar);
        AnimalBarDTO animalBarDTO = animalBarMapper.toDto(updatedAnimalBar);

        restAnimalBarMockMvc.perform(put("/api/animal-bars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalBarDTO)))
            .andExpect(status().isOk());

        // Validate the AnimalBar in the database
        List<AnimalBar> animalBarList = animalBarRepository.findAll();
        assertThat(animalBarList).hasSize(databaseSizeBeforeUpdate);
        AnimalBar testAnimalBar = animalBarList.get(animalBarList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingAnimalBar() throws Exception {
        int databaseSizeBeforeUpdate = animalBarRepository.findAll().size();

        // Create the AnimalBar
        AnimalBarDTO animalBarDTO = animalBarMapper.toDto(animalBar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnimalBarMockMvc.perform(put("/api/animal-bars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animalBarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnimalBar in the database
        List<AnimalBar> animalBarList = animalBarRepository.findAll();
        assertThat(animalBarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnimalBar() throws Exception {
        // Initialize the database
        animalBarRepository.saveAndFlush(animalBar);

        int databaseSizeBeforeDelete = animalBarRepository.findAll().size();

        // Delete the animalBar
        restAnimalBarMockMvc.perform(delete("/api/animal-bars/{id}", animalBar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnimalBar> animalBarList = animalBarRepository.findAll();
        assertThat(animalBarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalBar.class);
        AnimalBar animalBar1 = new AnimalBar();
        animalBar1.setId(1L);
        AnimalBar animalBar2 = new AnimalBar();
        animalBar2.setId(animalBar1.getId());
        assertThat(animalBar1).isEqualTo(animalBar2);
        animalBar2.setId(2L);
        assertThat(animalBar1).isNotEqualTo(animalBar2);
        animalBar1.setId(null);
        assertThat(animalBar1).isNotEqualTo(animalBar2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnimalBarDTO.class);
        AnimalBarDTO animalBarDTO1 = new AnimalBarDTO();
        animalBarDTO1.setId(1L);
        AnimalBarDTO animalBarDTO2 = new AnimalBarDTO();
        assertThat(animalBarDTO1).isNotEqualTo(animalBarDTO2);
        animalBarDTO2.setId(animalBarDTO1.getId());
        assertThat(animalBarDTO1).isEqualTo(animalBarDTO2);
        animalBarDTO2.setId(2L);
        assertThat(animalBarDTO1).isNotEqualTo(animalBarDTO2);
        animalBarDTO1.setId(null);
        assertThat(animalBarDTO1).isNotEqualTo(animalBarDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(animalBarMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(animalBarMapper.fromId(null)).isNull();
    }
}
