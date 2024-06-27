package com.cooksys.group_project_1_team_1.controllers;

import com.cooksys.group_project_1_team_1.models.*;
import com.cooksys.group_project_1_team_1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto){
        return userService.creatUser(userRequestDto);
    }

    @PostMapping("/@{username}/follow")
    public void followUser(@PathVariable String username, @RequestBody CredentialDto credentialsDto) {
        userService.followUser(username, credentialsDto);
    }

    @PostMapping("/@{username}/unfollow")
    public void unFollowUser(@PathVariable String username, @RequestBody CredentialDto credentials) {
        userService.unFollowUser(username, credentials);
    }

    @PatchMapping("/@{username}")
    public UserResponseDto updateProfileUsername(@PathVariable String username, @RequestBody UserRequestDto userRequestDto) {
        return userService.updateProfileUsername(username, userRequestDto);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/@{username}/feed")
    public List<TweetResponseDto> getUserFeed(@PathVariable("username") String username) {
        return userService.getFeedByUsername(username);
    }

    @GetMapping("/@{username}/tweets")
    public List<TweetResponseDto> getUserTweets(@PathVariable("username") String username) {
        return userService.getTweetsByUsername(username);
    }

    @GetMapping("/@{username}/mentions")
    public List<TweetResponseDto> getUserMentions(@PathVariable("username") String username) {
        return userService.getUserMentionsByUsername(username);
    }

    @GetMapping("/@{username}/following")
    public List<UserResponseDto> getUserFollowing(@PathVariable("username") String username) {
        return userService.getUserFollowingByUsername(username);
    }

    @GetMapping("/@{username}/followers")
    public List<UserResponseDto> getUserFollows(@PathVariable("username") String username) {
        return userService.getUserFollowsByUsername(username);
    }

    @GetMapping("/@{username}")
    public UserResponseDto getUser(@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }

    @DeleteMapping("/@{username}")
    public UserResponseDto deleteUserByUsername(@PathVariable("username") String username, @RequestBody CredentialDto credentials) {
        return userService.deleteUserByUsername(username, credentials);
    }
}

