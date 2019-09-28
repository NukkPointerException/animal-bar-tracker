package com.nukkpointer.animalbar.service.mapper;

import com.nukkpointer.animalbar.domain.*;
import com.nukkpointer.animalbar.service.dto.WrapperDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Wrapper} and its DTO {@link WrapperDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WrapperMapper extends EntityMapper<WrapperDTO, Wrapper> {



    default Wrapper fromId(Long id) {
        if (id == null) {
            return null;
        }
        Wrapper wrapper = new Wrapper();
        wrapper.setId(id);
        return wrapper;
    }
}
