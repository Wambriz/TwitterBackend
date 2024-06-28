package com.cooksys.group_project_1_team_1.services;

import com.cooksys.group_project_1_team_1.entities.Tweet;
import com.cooksys.group_project_1_team_1.models.*;

import java.util.List;

public interface TweetService {
    List<TweetResponseDto> getAllTweets();

    TweetResponseDto getTweet(Long id);

    Tweet getTweetFromDb(Long id);

    List<HashtagDto> getTweetHashtags(Long id);

    List<UserResponseDto> getTweetLikes(Long id);

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    List<TweetResponseDto> getTweetReposts(Long id);

    ContextDto getTweetContext(Long id);

    Object getTweetMentions(Long id);

    List<TweetResponseDto> getTweetReplies(Long id);

    TweetResponseDto repostTweet(Long id, CredentialDto credentialDto);

    TweetResponseDto replyToTweet(Long id, TweetRequestDto tweetRequestDto);

    TweetResponseDto deleteTweet(Long id, CredentialDto credentialDto);

    void likeTweet(Long id, CredentialDto credentialDto);
}
