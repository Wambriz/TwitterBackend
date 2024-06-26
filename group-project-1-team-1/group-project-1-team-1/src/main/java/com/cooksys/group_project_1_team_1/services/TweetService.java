package com.cooksys.group_project_1_team_1.services;

import com.cooksys.group_project_1_team_1.models.TweetResponseDto;

import java.util.List;

public interface TweetService {
    List<TweetResponseDto> getAllTweets();

    TweetResponseDto getTweet(Long id);
}
