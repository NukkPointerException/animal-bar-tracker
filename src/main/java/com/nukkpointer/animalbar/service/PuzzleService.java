package com.nukkpointer.animalbar.service;

import com.nukkpointer.animalbar.domain.Puzzle;
import com.nukkpointer.animalbar.repository.PuzzleRepository;
import com.nukkpointer.animalbar.service.dto.PuzzleDTO;
import com.nukkpointer.animalbar.service.mapper.PuzzleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Puzzle}.
 */
@Service
@Transactional
public class PuzzleService {

    private final Logger log = LoggerFactory.getLogger(PuzzleService.class);

    private final PuzzleRepository puzzleRepository;

    private final PuzzleMapper puzzleMapper;

    public PuzzleService(PuzzleRepository puzzleRepository, PuzzleMapper puzzleMapper) {
        this.puzzleRepository = puzzleRepository;
        this.puzzleMapper = puzzleMapper;
    }

    /**
     * Save a puzzle.
     *
     * @param puzzleDTO the entity to save.
     * @return the persisted entity.
     */
    public PuzzleDTO save(PuzzleDTO puzzleDTO) {
        log.debug("Request to save Puzzle : {}", puzzleDTO);
        Puzzle puzzle = puzzleMapper.toEntity(puzzleDTO);
        puzzle = puzzleRepository.save(puzzle);
        return puzzleMapper.toDto(puzzle);
    }

    /**
     * Get all the puzzles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PuzzleDTO> findAll() {
        log.debug("Request to get all Puzzles");
        return puzzleRepository.findAll().stream()
            .map(puzzleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one puzzle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PuzzleDTO> findOne(Long id) {
        log.debug("Request to get Puzzle : {}", id);
        return puzzleRepository.findById(id)
            .map(puzzleMapper::toDto);
    }

    /**
     * Delete the puzzle by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Puzzle : {}", id);
        puzzleRepository.deleteById(id);
    }
}
