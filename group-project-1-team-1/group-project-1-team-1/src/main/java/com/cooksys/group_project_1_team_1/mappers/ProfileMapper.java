package com.cooksys.group_project_1_team_1.mappers;

import com.cooksys.group_project_1_team_1.entities.Profile;
import com.cooksys.group_project_1_team_1.models.ProfileDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile requestDtoToEntity(ProfileDto profileDto);
}
