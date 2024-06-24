package com.cooksys.group_project_1_team_1.mappers;

import com.cooksys.group_project_1_team_1.entities.Hashtag;
import com.cooksys.group_project_1_team_1.models.HashtagDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
    Hashtag requestDtoToEntity(HashtagDto hashtagDto);

    HashtagDto entityToDto(Hashtag hashtag);

    List<HashtagDto> entitiesToDtos(List<Hashtag> hashtags);
}
