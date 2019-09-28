package com.nukkpointer.animalbar.web.rest;

import com.nukkpointer.animalbar.AnimalBarTrackerApp;
import com.nukkpointer.animalbar.domain.Wrapper;
import com.nukkpointer.animalbar.repository.WrapperRepository;
import com.nukkpointer.animalbar.service.WrapperService;
import com.nukkpointer.animalbar.service.dto.WrapperDTO;
import com.nukkpointer.animalbar.service.mapper.WrapperMapper;
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

import com.nukkpointer.animalbar.domain.enumeration.WrapperPicture;
/**
 * Integration tests for the {@link WrapperResource} REST controller.
 */
@SpringBootTest(classes = AnimalBarTrackerApp.class)
public class WrapperResourceIT {

    private static final WrapperPicture DEFAULT_PICTURE = WrapperPicture.MONKEY;
    private static final WrapperPicture UPDATED_PICTURE = WrapperPicture.LION;

    @Autowired
    private WrapperRepository wrapperRepository;

    @Autowired
    private WrapperMapper wrapperMapper;

    @Autowired
    private WrapperService wrapperService;

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

    private MockMvc restWrapperMockMvc;

    private Wrapper wrapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WrapperResource wrapperResource = new WrapperResource(wrapperService);
        this.restWrapperMockMvc = MockMvcBuilders.standaloneSetup(wrapperResource)
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
    public static Wrapper createEntity(EntityManager em) {
        Wrapper wrapper = new Wrapper()
            .picture(DEFAULT_PICTURE);
        return wrapper;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wrapper createUpdatedEntity(EntityManager em) {
        Wrapper wrapper = new Wrapper()
            .picture(UPDATED_PICTURE);
        return wrapper;
    }

    @BeforeEach
    public void initTest() {
        wrapper = createEntity(em);
    }

    @Test
    @Transactional
    public void createWrapper() throws Exception {
        int databaseSizeBeforeCreate = wrapperRepository.findAll().size();

        // Create the Wrapper
        WrapperDTO wrapperDTO = wrapperMapper.toDto(wrapper);
        restWrapperMockMvc.perform(post("/api/wrappers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wrapperDTO)))
            .andExpect(status().isCreated());

        // Validate the Wrapper in the database
        List<Wrapper> wrapperList = wrapperRepository.findAll();
        assertThat(wrapperList).hasSize(databaseSizeBeforeCreate + 1);
        Wrapper testWrapper = wrapperList.get(wrapperList.size() - 1);
        assertThat(testWrapper.getPicture()).isEqualTo(DEFAULT_PICTURE);
    }

    @Test
    @Transactional
    public void createWrapperWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wrapperRepository.findAll().size();

        // Create the Wrapper with an existing ID
        wrapper.setId(1L);
        WrapperDTO wrapperDTO = wrapperMapper.toDto(wrapper);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWrapperMockMvc.perform(post("/api/wrappers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wrapperDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wrapper in the database
        List<Wrapper> wrapperList = wrapperRepository.findAll();
        assertThat(wrapperList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPictureIsRequired() throws Exception {
        int databaseSizeBeforeTest = wrapperRepository.findAll().size();
        // set the field null
        wrapper.setPicture(null);

        // Create the Wrapper, which fails.
        WrapperDTO wrapperDTO = wrapperMapper.toDto(wrapper);

        restWrapperMockMvc.perform(post("/api/wrappers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wrapperDTO)))
            .andExpect(status().isBadRequest());

        List<Wrapper> wrapperList = wrapperRepository.findAll();
        assertThat(wrapperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWrappers() throws Exception {
        // Initialize the database
        wrapperRepository.saveAndFlush(wrapper);

        // Get all the wrapperList
        restWrapperMockMvc.perform(get("/api/wrappers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wrapper.getId().intValue())))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(DEFAULT_PICTURE.toString())));
    }
    
    @Test
    @Transactional
    public void getWrapper() throws Exception {
        // Initialize the database
        wrapperRepository.saveAndFlush(wrapper);

        // Get the wrapper
        restWrapperMockMvc.perform(get("/api/wrappers/{id}", wrapper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wrapper.getId().intValue()))
            .andExpect(jsonPath("$.picture").value(DEFAULT_PICTURE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWrapper() throws Exception {
        // Get the wrapper
        restWrapperMockMvc.perform(get("/api/wrappers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWrapper() throws Exception {
        // Initialize the database
        wrapperRepository.saveAndFlush(wrapper);

        int databaseSizeBeforeUpdate = wrapperRepository.findAll().size();

        // Update the wrapper
        Wrapper updatedWrapper = wrapperRepository.findById(wrapper.getId()).get();
        // Disconnect from session so that the updates on updatedWrapper are not directly saved in db
        em.detach(updatedWrapper);
        updatedWrapper
            .picture(UPDATED_PICTURE);
        WrapperDTO wrapperDTO = wrapperMapper.toDto(updatedWrapper);

        restWrapperMockMvc.perform(put("/api/wrappers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wrapperDTO)))
            .andExpect(status().isOk());

        // Validate the Wrapper in the database
        List<Wrapper> wrapperList = wrapperRepository.findAll();
        assertThat(wrapperList).hasSize(databaseSizeBeforeUpdate);
        Wrapper testWrapper = wrapperList.get(wrapperList.size() - 1);
        assertThat(testWrapper.getPicture()).isEqualTo(UPDATED_PICTURE);
    }

    @Test
    @Transactional
    public void updateNonExistingWrapper() throws Exception {
        int databaseSizeBeforeUpdate = wrapperRepository.findAll().size();

        // Create the Wrapper
        WrapperDTO wrapperDTO = wrapperMapper.toDto(wrapper);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWrapperMockMvc.perform(put("/api/wrappers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wrapperDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Wrapper in the database
        List<Wrapper> wrapperList = wrapperRepository.findAll();
        assertThat(wrapperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWrapper() throws Exception {
        // Initialize the database
        wrapperRepository.saveAndFlush(wrapper);

        int databaseSizeBeforeDelete = wrapperRepository.findAll().size();

        // Delete the wrapper
        restWrapperMockMvc.perform(delete("/api/wrappers/{id}", wrapper.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Wrapper> wrapperList = wrapperRepository.findAll();
        assertThat(wrapperList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wrapper.class);
        Wrapper wrapper1 = new Wrapper();
        wrapper1.setId(1L);
        Wrapper wrapper2 = new Wrapper();
        wrapper2.setId(wrapper1.getId());
        assertThat(wrapper1).isEqualTo(wrapper2);
        wrapper2.setId(2L);
        assertThat(wrapper1).isNotEqualTo(wrapper2);
        wrapper1.setId(null);
        assertThat(wrapper1).isNotEqualTo(wrapper2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WrapperDTO.class);
        WrapperDTO wrapperDTO1 = new WrapperDTO();
        wrapperDTO1.setId(1L);
        WrapperDTO wrapperDTO2 = new WrapperDTO();
        assertThat(wrapperDTO1).isNotEqualTo(wrapperDTO2);
        wrapperDTO2.setId(wrapperDTO1.getId());
        assertThat(wrapperDTO1).isEqualTo(wrapperDTO2);
        wrapperDTO2.setId(2L);
        assertThat(wrapperDTO1).isNotEqualTo(wrapperDTO2);
        wrapperDTO1.setId(null);
        assertThat(wrapperDTO1).isNotEqualTo(wrapperDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(wrapperMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(wrapperMapper.fromId(null)).isNull();
    }
}
