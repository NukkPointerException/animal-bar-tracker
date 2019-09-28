package com.nukkpointer.animalbar.service.mapper;

import com.nukkpointer.animalbar.domain.*;
import com.nukkpointer.animalbar.service.dto.AppUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppUser} and its DTO {@link AppUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {


    @Mapping(target = "entries", ignore = true)
    @Mapping(target = "removeEntries", ignore = true)
    AppUser toEntity(AppUserDTO appUserDTO);

    default AppUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        AppUser appUser = new AppUser();
        appUser.setId(id);
        return appUser;
    }
}
