package com.cooksys.group_project_1_team_1.mappers;

import com.cooksys.group_project_1_team_1.entities.Credentials;
import com.cooksys.group_project_1_team_1.models.CredentialDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {
    Credentials requestDtoToEntity(CredentialDto credentialDto);
}
