package com.nukkpointer.animalbar.service;

import com.nukkpointer.animalbar.domain.Wrapper;
import com.nukkpointer.animalbar.repository.WrapperRepository;
import com.nukkpointer.animalbar.service.dto.WrapperDTO;
import com.nukkpointer.animalbar.service.mapper.WrapperMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Wrapper}.
 */
@Service
@Transactional
public class WrapperService {

    private final Logger log = LoggerFactory.getLogger(WrapperService.class);

    private final WrapperRepository wrapperRepository;

    private final WrapperMapper wrapperMapper;

    public WrapperService(WrapperRepository wrapperRepository, WrapperMapper wrapperMapper) {
        this.wrapperRepository = wrapperRepository;
        this.wrapperMapper = wrapperMapper;
    }

    /**
     * Save a wrapper.
     *
     * @param wrapperDTO the entity to save.
     * @return the persisted entity.
     */
    public WrapperDTO save(WrapperDTO wrapperDTO) {
        log.debug("Request to save Wrapper : {}", wrapperDTO);
        Wrapper wrapper = wrapperMapper.toEntity(wrapperDTO);
        wrapper = wrapperRepository.save(wrapper);
        return wrapperMapper.toDto(wrapper);
    }

    /**
     * Get all the wrappers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<WrapperDTO> findAll() {
        log.debug("Request to get all Wrappers");
        return wrapperRepository.findAll().stream()
            .map(wrapperMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one wrapper by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WrapperDTO> findOne(Long id) {
        log.debug("Request to get Wrapper : {}", id);
        return wrapperRepository.findById(id)
            .map(wrapperMapper::toDto);
    }

    /**
     * Delete the wrapper by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Wrapper : {}", id);
        wrapperRepository.deleteById(id);
    }
}
