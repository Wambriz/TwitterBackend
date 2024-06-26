package com.cooksys.group_project_1_team_1.services;

import com.cooksys.group_project_1_team_1.models.CredentialDto;
import com.cooksys.group_project_1_team_1.models.TweetResponseDto;
import com.cooksys.group_project_1_team_1.models.UserRequestDto;
import com.cooksys.group_project_1_team_1.models.UserResponseDto;

import java.util.List;


public interface UserService {

    List<TweetResponseDto> getFeedByUsername(String username);

    UserResponseDto deleteUserByUsername(String username, CredentialDto credentials);

    UserResponseDto getUserByUsername(String username);

    List<TweetResponseDto> getTweetsByUsername(String username);

    List<TweetResponseDto> getUserMentionsByUsername(String username);

    List<UserResponseDto> getUserFollowingByUsername(String username);

    List<UserResponseDto> getUserFollowsByUsername(String username);

    List<UserResponseDto> getAllUsers();

    UserResponseDto creatUser(UserRequestDto userRequestDto);

    void followUser(String username, CredentialDto credentialsDto);
}
