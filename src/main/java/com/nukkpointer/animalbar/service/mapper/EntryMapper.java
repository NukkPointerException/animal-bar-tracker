package com.nukkpointer.animalbar.service.mapper;

import com.nukkpointer.animalbar.domain.*;
import com.nukkpointer.animalbar.service.dto.EntryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Entry} and its DTO {@link EntryDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnimalBarMapper.class, AppUserMapper.class})
public interface EntryMapper extends EntityMapper<EntryDTO, Entry> {

    @Mapping(source = "bar.id", target = "barId")
    @Mapping(source = "appUser.id", target = "appUserId")
    EntryDTO toDto(Entry entry);

    @Mapping(source = "barId", target = "bar")
    @Mapping(source = "appUserId", target = "appUser")
    Entry toEntity(EntryDTO entryDTO);

    default Entry fromId(Long id) {
        if (id == null) {
            return null;
        }
        Entry entry = new Entry();
        entry.setId(id);
        return entry;
    }
}
