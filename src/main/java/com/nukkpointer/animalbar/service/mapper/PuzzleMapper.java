package com.nukkpointer.animalbar.service.mapper;

import com.nukkpointer.animalbar.domain.*;
import com.nukkpointer.animalbar.service.dto.PuzzleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Puzzle} and its DTO {@link PuzzleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PuzzleMapper extends EntityMapper<PuzzleDTO, Puzzle> {



    default Puzzle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Puzzle puzzle = new Puzzle();
        puzzle.setId(id);
        return puzzle;
    }
}
