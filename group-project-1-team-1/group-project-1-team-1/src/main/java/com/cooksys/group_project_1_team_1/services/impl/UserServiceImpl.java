package com.cooksys.group_project_1_team_1.services.impl;

import com.cooksys.group_project_1_team_1.mappers.TweetMapper;
import com.cooksys.group_project_1_team_1.models.TweetResponseDto;
import com.cooksys.group_project_1_team_1.repositories.UserRepository;
import com.cooksys.group_project_1_team_1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TweetMapper tweetMapper;

    @Override
    public List<TweetResponseDto> getFeedByUsername(String username) {

        return tweetMapper.entitiesToResponseDtos(userRepository.findByCredentialsUsername(username).getTweets());
    }
}
