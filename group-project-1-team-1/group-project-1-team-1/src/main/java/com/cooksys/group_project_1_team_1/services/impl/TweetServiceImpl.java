package com.cooksys.group_project_1_team_1.services.impl;

import com.cooksys.group_project_1_team_1.entities.Tweet;
import com.cooksys.group_project_1_team_1.exceptions.NotFoundException;
import com.cooksys.group_project_1_team_1.mappers.TweetMapper;
import com.cooksys.group_project_1_team_1.models.TweetResponseDto;
import com.cooksys.group_project_1_team_1.repositories.TweetRepository;
import com.cooksys.group_project_1_team_1.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;

    @Override
    public List<TweetResponseDto> getAllTweets() {
        List<Tweet> tweets = tweetRepository.findAllByDeletedFalse();
        List<Tweet> filteredTweets = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (!tweet.isDeleted()) {
                filteredTweets.add(tweet);
            }
        }
        filteredTweets.sort(Comparator.comparing(Tweet::getPosted).reversed());
        return tweetMapper.entitiesToResponseDtos(filteredTweets);
    }

    @Override
    public TweetResponseDto getTweet(Long id) {
        Optional<Tweet> tweet=tweetRepository.findById(id);
        if (tweet.isEmpty()){
            throw new NotFoundException("Tweet with id "+id+" can not be found");
        }
        return tweetMapper.entityToResponseDto(tweet.get());

    }
}
