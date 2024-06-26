package com.cooksys.group_project_1_team_1.controllers;

import com.cooksys.group_project_1_team_1.models.CredentialDto;
import com.cooksys.group_project_1_team_1.models.TweetResponseDto;
import com.cooksys.group_project_1_team_1.models.UserRequestDto;
import com.cooksys.group_project_1_team_1.models.UserResponseDto;
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

    @GetMapping("/@{username}/follows")
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

