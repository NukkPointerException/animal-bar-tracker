package com.nukkpointer.animalbar.service.mapper;

import com.nukkpointer.animalbar.domain.*;
import com.nukkpointer.animalbar.service.dto.ChocolateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Chocolate} and its DTO {@link ChocolateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ChocolateMapper extends EntityMapper<ChocolateDTO, Chocolate> {



    default Chocolate fromId(Long id) {
        if (id == null) {
            return null;
        }
        Chocolate chocolate = new Chocolate();
        chocolate.setId(id);
        return chocolate;
    }
}
