package com.nukkpointer.animalbar.web.rest;

import com.nukkpointer.animalbar.AnimalBarTrackerApp;
import com.nukkpointer.animalbar.domain.Chocolate;
import com.nukkpointer.animalbar.repository.ChocolateRepository;
import com.nukkpointer.animalbar.service.ChocolateService;
import com.nukkpointer.animalbar.service.dto.ChocolateDTO;
import com.nukkpointer.animalbar.service.mapper.ChocolateMapper;
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

import com.nukkpointer.animalbar.domain.enumeration.ChocolatePicture;
import com.nukkpointer.animalbar.domain.enumeration.ChocolatePicture;
/**
 * Integration tests for the {@link ChocolateResource} REST controller.
 */
@SpringBootTest(classes = AnimalBarTrackerApp.class)
public class ChocolateResourceIT {

    private static final ChocolatePicture DEFAULT_LEFT_IMAGE = ChocolatePicture.LION;
    private static final ChocolatePicture UPDATED_LEFT_IMAGE = ChocolatePicture.ZEBRA;

    private static final ChocolatePicture DEFAULT_RIGHT_IMAGE = ChocolatePicture.LION;
    private static final ChocolatePicture UPDATED_RIGHT_IMAGE = ChocolatePicture.ZEBRA;

    @Autowired
    private ChocolateRepository chocolateRepository;

    @Autowired
    private ChocolateMapper chocolateMapper;

    @Autowired
    private ChocolateService chocolateService;

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

    private MockMvc restChocolateMockMvc;

    private Chocolate chocolate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChocolateResource chocolateResource = new ChocolateResource(chocolateService);
        this.restChocolateMockMvc = MockMvcBuilders.standaloneSetup(chocolateResource)
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
    public static Chocolate createEntity(EntityManager em) {
        Chocolate chocolate = new Chocolate()
            .leftImage(DEFAULT_LEFT_IMAGE)
            .rightImage(DEFAULT_RIGHT_IMAGE);
        return chocolate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chocolate createUpdatedEntity(EntityManager em) {
        Chocolate chocolate = new Chocolate()
            .leftImage(UPDATED_LEFT_IMAGE)
            .rightImage(UPDATED_RIGHT_IMAGE);
        return chocolate;
    }

    @BeforeEach
    public void initTest() {
        chocolate = createEntity(em);
    }

    @Test
    @Transactional
    public void createChocolate() throws Exception {
        int databaseSizeBeforeCreate = chocolateRepository.findAll().size();

        // Create the Chocolate
        ChocolateDTO chocolateDTO = chocolateMapper.toDto(chocolate);
        restChocolateMockMvc.perform(post("/api/chocolates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chocolateDTO)))
            .andExpect(status().isCreated());

        // Validate the Chocolate in the database
        List<Chocolate> chocolateList = chocolateRepository.findAll();
        assertThat(chocolateList).hasSize(databaseSizeBeforeCreate + 1);
        Chocolate testChocolate = chocolateList.get(chocolateList.size() - 1);
        assertThat(testChocolate.getLeftImage()).isEqualTo(DEFAULT_LEFT_IMAGE);
        assertThat(testChocolate.getRightImage()).isEqualTo(DEFAULT_RIGHT_IMAGE);
    }

    @Test
    @Transactional
    public void createChocolateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chocolateRepository.findAll().size();

        // Create the Chocolate with an existing ID
        chocolate.setId(1L);
        ChocolateDTO chocolateDTO = chocolateMapper.toDto(chocolate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChocolateMockMvc.perform(post("/api/chocolates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chocolateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chocolate in the database
        List<Chocolate> chocolateList = chocolateRepository.findAll();
        assertThat(chocolateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLeftImageIsRequired() throws Exception {
        int databaseSizeBeforeTest = chocolateRepository.findAll().size();
        // set the field null
        chocolate.setLeftImage(null);

        // Create the Chocolate, which fails.
        ChocolateDTO chocolateDTO = chocolateMapper.toDto(chocolate);

        restChocolateMockMvc.perform(post("/api/chocolates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chocolateDTO)))
            .andExpect(status().isBadRequest());

        List<Chocolate> chocolateList = chocolateRepository.findAll();
        assertThat(chocolateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRightImageIsRequired() throws Exception {
        int databaseSizeBeforeTest = chocolateRepository.findAll().size();
        // set the field null
        chocolate.setRightImage(null);

        // Create the Chocolate, which fails.
        ChocolateDTO chocolateDTO = chocolateMapper.toDto(chocolate);

        restChocolateMockMvc.perform(post("/api/chocolates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chocolateDTO)))
            .andExpect(status().isBadRequest());

        List<Chocolate> chocolateList = chocolateRepository.findAll();
        assertThat(chocolateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChocolates() throws Exception {
        // Initialize the database
        chocolateRepository.saveAndFlush(chocolate);

        // Get all the chocolateList
        restChocolateMockMvc.perform(get("/api/chocolates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chocolate.getId().intValue())))
            .andExpect(jsonPath("$.[*].leftImage").value(hasItem(DEFAULT_LEFT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].rightImage").value(hasItem(DEFAULT_RIGHT_IMAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getChocolate() throws Exception {
        // Initialize the database
        chocolateRepository.saveAndFlush(chocolate);

        // Get the chocolate
        restChocolateMockMvc.perform(get("/api/chocolates/{id}", chocolate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chocolate.getId().intValue()))
            .andExpect(jsonPath("$.leftImage").value(DEFAULT_LEFT_IMAGE.toString()))
            .andExpect(jsonPath("$.rightImage").value(DEFAULT_RIGHT_IMAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChocolate() throws Exception {
        // Get the chocolate
        restChocolateMockMvc.perform(get("/api/chocolates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChocolate() throws Exception {
        // Initialize the database
        chocolateRepository.saveAndFlush(chocolate);

        int databaseSizeBeforeUpdate = chocolateRepository.findAll().size();

        // Update the chocolate
        Chocolate updatedChocolate = chocolateRepository.findById(chocolate.getId()).get();
        // Disconnect from session so that the updates on updatedChocolate are not directly saved in db
        em.detach(updatedChocolate);
        updatedChocolate
            .leftImage(UPDATED_LEFT_IMAGE)
            .rightImage(UPDATED_RIGHT_IMAGE);
        ChocolateDTO chocolateDTO = chocolateMapper.toDto(updatedChocolate);

        restChocolateMockMvc.perform(put("/api/chocolates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chocolateDTO)))
            .andExpect(status().isOk());

        // Validate the Chocolate in the database
        List<Chocolate> chocolateList = chocolateRepository.findAll();
        assertThat(chocolateList).hasSize(databaseSizeBeforeUpdate);
        Chocolate testChocolate = chocolateList.get(chocolateList.size() - 1);
        assertThat(testChocolate.getLeftImage()).isEqualTo(UPDATED_LEFT_IMAGE);
        assertThat(testChocolate.getRightImage()).isEqualTo(UPDATED_RIGHT_IMAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingChocolate() throws Exception {
        int databaseSizeBeforeUpdate = chocolateRepository.findAll().size();

        // Create the Chocolate
        ChocolateDTO chocolateDTO = chocolateMapper.toDto(chocolate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChocolateMockMvc.perform(put("/api/chocolates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chocolateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chocolate in the database
        List<Chocolate> chocolateList = chocolateRepository.findAll();
        assertThat(chocolateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChocolate() throws Exception {
        // Initialize the database
        chocolateRepository.saveAndFlush(chocolate);

        int databaseSizeBeforeDelete = chocolateRepository.findAll().size();

        // Delete the chocolate
        restChocolateMockMvc.perform(delete("/api/chocolates/{id}", chocolate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chocolate> chocolateList = chocolateRepository.findAll();
        assertThat(chocolateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chocolate.class);
        Chocolate chocolate1 = new Chocolate();
        chocolate1.setId(1L);
        Chocolate chocolate2 = new Chocolate();
        chocolate2.setId(chocolate1.getId());
        assertThat(chocolate1).isEqualTo(chocolate2);
        chocolate2.setId(2L);
        assertThat(chocolate1).isNotEqualTo(chocolate2);
        chocolate1.setId(null);
        assertThat(chocolate1).isNotEqualTo(chocolate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChocolateDTO.class);
        ChocolateDTO chocolateDTO1 = new ChocolateDTO();
        chocolateDTO1.setId(1L);
        ChocolateDTO chocolateDTO2 = new ChocolateDTO();
        assertThat(chocolateDTO1).isNotEqualTo(chocolateDTO2);
        chocolateDTO2.setId(chocolateDTO1.getId());
        assertThat(chocolateDTO1).isEqualTo(chocolateDTO2);
        chocolateDTO2.setId(2L);
        assertThat(chocolateDTO1).isNotEqualTo(chocolateDTO2);
        chocolateDTO1.setId(null);
        assertThat(chocolateDTO1).isNotEqualTo(chocolateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(chocolateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(chocolateMapper.fromId(null)).isNull();
    }
}
