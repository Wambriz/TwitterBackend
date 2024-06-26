package com.cooksys.group_project_1_team_1.services.impl;

import com.cooksys.group_project_1_team_1.entities.Credentials;
import com.cooksys.group_project_1_team_1.entities.Tweet;
import com.cooksys.group_project_1_team_1.entities.User;
import com.cooksys.group_project_1_team_1.exceptions.BadRequestException;
import com.cooksys.group_project_1_team_1.exceptions.NotAuthorizedException;
import com.cooksys.group_project_1_team_1.exceptions.NotFoundException;
import com.cooksys.group_project_1_team_1.mappers.CredentialsMapper;
import com.cooksys.group_project_1_team_1.mappers.TweetMapper;
import com.cooksys.group_project_1_team_1.mappers.UserMapper;
import com.cooksys.group_project_1_team_1.models.CredentialDto;
import com.cooksys.group_project_1_team_1.models.TweetResponseDto;
import com.cooksys.group_project_1_team_1.models.UserRequestDto;
import com.cooksys.group_project_1_team_1.models.UserResponseDto;
import com.cooksys.group_project_1_team_1.repositories.TweetRepository;
import com.cooksys.group_project_1_team_1.repositories.UserRepository;
import com.cooksys.group_project_1_team_1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CredentialsMapper credentialsMapper;
    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    private void validateUserRequest(UserRequestDto userRequestDto) {
        if (userRequestDto.getCredentials() == null) {
            throw new BadRequestException("Must provide your credentials.");
        }
        if (userRequestDto.getProfile() == null) {
            throw new BadRequestException("Must provide your profile.");
        }

        if (userRequestDto.getCredentials().getUsername() == null) {
            throw new BadRequestException("Must provide your username.");
        }
        if (userRequestDto.getCredentials().getPassword() == null
                || userRequestDto.getCredentials().getPassword().length() == 0) {
            throw new BadRequestException("Must provide your password.");
        }

    }

    private void validateCredentials(CredentialDto credentialsRequestDto) {
        if (credentialsRequestDto.getUsername() == null) {
            throw new BadRequestException("Must provide your username.");
        }
        if (credentialsRequestDto.getPassword() == null) {
            throw new BadRequestException("Must provide your password.");
        }
    }

    @Override
    public List<TweetResponseDto> getFeedByUsername(String username) {
        User user = userRepository.findByCredentialsUsername(username);
        if (user == null || user.isDeleted()) {
            throw new NotFoundException("User not found");
        }

        List<Tweet> tweets = tweetRepository.findAllByAuthorIdAndDeletedFalse(user.getId());
        return tweetMapper.entitiesToResponseDtos(tweets);
    }

    @Override
    public UserResponseDto deleteUserByUsername(String username, CredentialDto credentials) {
        User user = userRepository.findByCredentialsUsername(username);
        if (user == null || user.isDeleted()) {
            throw new NotFoundException("User not found");
        }
        if (!user.getCredentials().equals(credentialsMapper.requestDtoToEntity(credentials))) {
            throw new BadRequestException("Credentials for user you are trying to delete do not match.");
        }

        user.setDeleted(true);
        for (Tweet tweet : user.getTweets()) {
            tweet.setDeleted(true);
            tweetRepository.saveAndFlush(tweet);
        }
        userRepository.saveAndFlush(user);

        return userMapper.entityToResponseDto(user);
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByCredentialsUsername(username);
        if (user == null || user.isDeleted()) {
            throw new NotFoundException("User not found");
        }

        return userMapper.entityToResponseDto(user);
    }

    @Override
    public List<TweetResponseDto> getTweetsByUsername(String username) {
        User user = userRepository.findByCredentialsUsername(username);
        if (user == null || user.isDeleted()) {
            throw new NotFoundException("User not found");
        }
        user.getTweets().sort(Comparator.comparing(Tweet::getPosted).reversed());

        return tweetMapper.entitiesToResponseDtos(user.getTweets());
    }

    @Override
    public List<TweetResponseDto> getUserMentionsByUsername(String username) {
        User user = userRepository.findByCredentialsUsername(username);
        if (user == null || user.isDeleted()) {
            throw new NotFoundException("User not found");
        }

        List<Tweet> mentionedTweets = new ArrayList<>();

        for (Tweet tweet : tweetRepository.findAll()) {
            if (tweet.getContent() != null && tweet.getContent().contains("@" + username)) {
                mentionedTweets.add(tweet);
                System.out.println("Found one");
            }
        }

        mentionedTweets.sort(Comparator.comparing(Tweet::getPosted).reversed());

        return tweetMapper.entitiesToResponseDtos(mentionedTweets);
    }

    @Override
    public List<UserResponseDto> getUserFollowingByUsername(String username) {
        User user = userRepository.findByCredentialsUsername(username);
        if (user == null || user.isDeleted()) {
            throw new NotFoundException("User not found");
        }

        List<User> following = new ArrayList<>();

        for (User u : user.getFollowing()) {
            if (!u.isDeleted()) {
                following.add(u);
            }
        }

        return userMapper.entitiesToResponseDtos(following);
    }

    @Override
    public List<UserResponseDto> getUserFollowsByUsername(String username) {
        User user = userRepository.findByCredentialsUsername(username);
        if (user == null || user.isDeleted()) {
            throw new NotFoundException("User not found");
        }

        List<User> follows = new ArrayList<>();

        for (User u : user.getFollowers()) {
            if (!u.isDeleted()) {
                follows.add(u);
            }
        }

        return userMapper.entitiesToResponseDtos(follows);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userMapper.entitiesToResponseDtos(userRepository.findAllByDeletedFalse());
    }

    @Override
    public UserResponseDto creatUser(UserRequestDto userRequestDto) {
        validateUserRequest(userRequestDto);
        validateCredentials(userRequestDto.getCredentials());
        if (userRequestDto.getProfile().getEmail() == null) {
            throw new BadRequestException("Must provide your email to create a new user.");
        }

        User user = userRepository.findByCredentialsUsername(userRequestDto.getCredentials().getUsername());
        if (user != null && !user.isDeleted()) {
            throw new BadRequestException("Username is not available.");
        }
        if (user != null && user.isDeleted()) {
            user.setDeleted(false);

            if (userRequestDto.getProfile().getEmail() != null) {
                user.getProfile().setEmail(userRequestDto.getProfile().getEmail());
            }
            if (userRequestDto.getProfile().getFirstName() != null) {
                user.getProfile().setFirstName(userRequestDto.getProfile().getFirstName());
            }
            if (userRequestDto.getProfile().getLastName() != null) {
                user.getProfile().setLastName(userRequestDto.getProfile().getLastName());
            }
            if (userRequestDto.getProfile().getPhone() != null) {
                user.getProfile().setPhone(userRequestDto.getProfile().getPhone());
            }

            return userMapper.entityToResponseDto(userRepository.saveAndFlush(user));
        }
        User userToSave = userMapper.requestDtoToEntity(userRequestDto);
        userToSave.setDeleted(false);

        return userMapper.entityToResponseDto(userRepository.saveAndFlush(userToSave));
    }

    @Override
    public void followUser(String username, CredentialDto credentialsDto) {
        validateCredentials(credentialsDto);
        User userToFollow = userRepository.findByCredentialsUsername(username);
        if (userToFollow == null || userToFollow.isDeleted()) {
            throw new NotFoundException("User not found");
        }
        Credentials credentials = credentialsMapper.requestDtoToEntity(credentialsDto);
        User currentUser = userRepository.findByCredentialsUsername(credentials.getUsername());
        if (currentUser== null || currentUser.isDeleted()) {
            throw new NotFoundException("User not found");
        }
        if (!currentUser.getCredentials().getPassword().equals(credentials.getPassword())) {
            throw new NotAuthorizedException("Password is incorrect.");
        }
        
        for (User u : currentUser.getFollowing()) {
            if (u.getCredentials().getUsername().equals(userToFollow.getCredentials().getUsername())) {
                throw new BadRequestException("You already follow this user.");
            }
        }
        userToFollow.getFollowers().add(currentUser);
        userMapper.entityToResponseDto(userRepository.saveAndFlush(currentUser));
        userMapper.entityToResponseDto(userRepository.saveAndFlush(userToFollow));
    }
}
