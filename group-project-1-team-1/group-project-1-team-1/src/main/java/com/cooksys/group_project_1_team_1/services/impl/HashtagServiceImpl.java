package com.cooksys.group_project_1_team_1.services.impl;

import com.cooksys.group_project_1_team_1.entities.Hashtag;
import com.cooksys.group_project_1_team_1.entities.Tweet;
import com.cooksys.group_project_1_team_1.exceptions.NotFoundException;
import com.cooksys.group_project_1_team_1.mappers.HashtagMapper;
import com.cooksys.group_project_1_team_1.mappers.TweetMapper;
import com.cooksys.group_project_1_team_1.models.HashtagDto;
import com.cooksys.group_project_1_team_1.models.TweetResponseDto;
import com.cooksys.group_project_1_team_1.repositories.HashtagRepository;
import com.cooksys.group_project_1_team_1.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
    private final HashtagMapper hashtagMapper;
    private final HashtagRepository hashtagRepository;
    private final TweetMapper tweetMapper;

    @Override
    public List<HashtagDto> getAllTags() {
        return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
    }

    @Override
    public List<TweetResponseDto> getTweetsByTagLabel(String label) {
        Hashtag tag=hashtagRepository.findByLabelIgnoreCase(label);
        if (tag == null) {
            throw new NotFoundException("Can not find tag with that label");
        }
        List<Tweet> tweetsWithTag=tag.getTweets();
        tweetsWithTag.sort(Comparator.comparing(Tweet::getPosted).reversed());
        return tweetMapper.entitiesToResponseDtos(tweetsWithTag);
    }
}
