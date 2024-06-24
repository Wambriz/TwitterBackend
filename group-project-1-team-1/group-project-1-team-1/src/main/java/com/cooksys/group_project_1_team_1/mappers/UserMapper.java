package com.cooksys.group_project_1_team_1.mappers;

import com.cooksys.group_project_1_team_1.entities.User;
import com.cooksys.group_project_1_team_1.models.UserRequestDto;
import com.cooksys.group_project_1_team_1.models.UserResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = {ProfileMapper.class, CredentialsMapper.class})
public interface UserMapper {
    User requestDtoToEntity(UserRequestDto userRequestDto);

    UserResponseDto entityToResponseDto(User user);

    List<UserResponseDto> entitiesToResponseDtos(List<User> users);
}
