package com.nukkpointer.animalbar.service;

import com.nukkpointer.animalbar.domain.Chocolate;
import com.nukkpointer.animalbar.repository.ChocolateRepository;
import com.nukkpointer.animalbar.service.dto.ChocolateDTO;
import com.nukkpointer.animalbar.service.mapper.ChocolateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Chocolate}.
 */
@Service
@Transactional
public class ChocolateService {

    private final Logger log = LoggerFactory.getLogger(ChocolateService.class);

    private final ChocolateRepository chocolateRepository;

    private final ChocolateMapper chocolateMapper;

    public ChocolateService(ChocolateRepository chocolateRepository, ChocolateMapper chocolateMapper) {
        this.chocolateRepository = chocolateRepository;
        this.chocolateMapper = chocolateMapper;
    }

    /**
     * Save a chocolate.
     *
     * @param chocolateDTO the entity to save.
     * @return the persisted entity.
     */
    public ChocolateDTO save(ChocolateDTO chocolateDTO) {
        log.debug("Request to save Chocolate : {}", chocolateDTO);
        Chocolate chocolate = chocolateMapper.toEntity(chocolateDTO);
        chocolate = chocolateRepository.save(chocolate);
        return chocolateMapper.toDto(chocolate);
    }

    /**
     * Get all the chocolates.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ChocolateDTO> findAll() {
        log.debug("Request to get all Chocolates");
        return chocolateRepository.findAll().stream()
            .map(chocolateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one chocolate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ChocolateDTO> findOne(Long id) {
        log.debug("Request to get Chocolate : {}", id);
        return chocolateRepository.findById(id)
            .map(chocolateMapper::toDto);
    }

    /**
     * Delete the chocolate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Chocolate : {}", id);
        chocolateRepository.deleteById(id);
    }
}
