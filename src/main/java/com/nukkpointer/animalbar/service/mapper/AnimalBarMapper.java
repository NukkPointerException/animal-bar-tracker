package com.nukkpointer.animalbar.service.mapper;

import com.nukkpointer.animalbar.domain.*;
import com.nukkpointer.animalbar.service.dto.AnimalBarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnimalBar} and its DTO {@link AnimalBarDTO}.
 */
@Mapper(componentModel = "spring", uses = {WrapperMapper.class, PuzzleMapper.class, ChocolateMapper.class})
public interface AnimalBarMapper extends EntityMapper<AnimalBarDTO, AnimalBar> {

    @Mapping(source = "wrapper.id", target = "wrapperId")
    @Mapping(source = "puzzle.id", target = "puzzleId")
    @Mapping(source = "chocolate.id", target = "chocolateId")
    AnimalBarDTO toDto(AnimalBar animalBar);

    @Mapping(source = "wrapperId", target = "wrapper")
    @Mapping(source = "puzzleId", target = "puzzle")
    @Mapping(source = "chocolateId", target = "chocolate")
    AnimalBar toEntity(AnimalBarDTO animalBarDTO);

    default AnimalBar fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnimalBar animalBar = new AnimalBar();
        animalBar.setId(id);
        return animalBar;
    }
}
