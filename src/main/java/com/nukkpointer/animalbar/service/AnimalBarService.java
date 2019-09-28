package com.nukkpointer.animalbar.service;

import com.nukkpointer.animalbar.domain.AnimalBar;
import com.nukkpointer.animalbar.repository.AnimalBarRepository;
import com.nukkpointer.animalbar.service.dto.AnimalBarDTO;
import com.nukkpointer.animalbar.service.mapper.AnimalBarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AnimalBar}.
 */
@Service
@Transactional
public class AnimalBarService {

    private final Logger log = LoggerFactory.getLogger(AnimalBarService.class);

    private final AnimalBarRepository animalBarRepository;

    private final AnimalBarMapper animalBarMapper;

    public AnimalBarService(AnimalBarRepository animalBarRepository, AnimalBarMapper animalBarMapper) {
        this.animalBarRepository = animalBarRepository;
        this.animalBarMapper = animalBarMapper;
    }

    /**
     * Save a animalBar.
     *
     * @param animalBarDTO the entity to save.
     * @return the persisted entity.
     */
    public AnimalBarDTO save(AnimalBarDTO animalBarDTO) {
        log.debug("Request to save AnimalBar : {}", animalBarDTO);
        AnimalBar animalBar = animalBarMapper.toEntity(animalBarDTO);
        animalBar = animalBarRepository.save(animalBar);
        return animalBarMapper.toDto(animalBar);
    }

    /**
     * Get all the animalBars.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AnimalBarDTO> findAll() {
        log.debug("Request to get all AnimalBars");
        return animalBarRepository.findAll().stream()
            .map(animalBarMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one animalBar by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnimalBarDTO> findOne(Long id) {
        log.debug("Request to get AnimalBar : {}", id);
        return animalBarRepository.findById(id)
            .map(animalBarMapper::toDto);
    }

    /**
     * Delete the animalBar by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnimalBar : {}", id);
        animalBarRepository.deleteById(id);
    }
}
