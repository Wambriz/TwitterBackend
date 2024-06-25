package com.cooksys.group_project_1_team_1.mappers;

import com.cooksys.group_project_1_team_1.entities.Tweet;
import com.cooksys.group_project_1_team_1.models.TweetRequestDto;
import com.cooksys.group_project_1_team_1.models.TweetResponseDto;

public interface TweetMapper {
    Tweet requestDtoToEntity(TweetRequestDto tweetRequestDto);
    Tweet responseDtoToEntity(TweetResponseDto tweetResponseDto);
    TweetRequestDto entityToRequestDto(Tweet tweet);
    TweetResponseDto entityToResponseDto(Tweet tweet);
}
