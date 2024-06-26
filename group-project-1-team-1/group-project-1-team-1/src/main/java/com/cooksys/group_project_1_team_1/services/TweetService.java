package com.cooksys.group_project_1_team_1.services;

import com.cooksys.group_project_1_team_1.entities.Tweet;
import com.cooksys.group_project_1_team_1.models.HashtagDto;
import com.cooksys.group_project_1_team_1.models.TweetRequestDto;
import com.cooksys.group_project_1_team_1.models.TweetResponseDto;
import com.cooksys.group_project_1_team_1.models.UserResponseDto;

import java.util.List;

public interface TweetService {
    List<TweetResponseDto> getAllTweets();

    TweetResponseDto getTweet(Long id);

    Tweet getTweetFromDb(Long id);

    List<HashtagDto> getTweetHashtags(Long id);

    List<UserResponseDto> getTweetLikes(Long id);

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);
}
