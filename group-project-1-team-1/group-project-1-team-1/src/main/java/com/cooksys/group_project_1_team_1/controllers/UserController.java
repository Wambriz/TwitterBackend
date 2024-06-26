package com.cooksys.group_project_1_team_1.controllers;

import com.cooksys.group_project_1_team_1.models.TweetResponseDto;
import com.cooksys.group_project_1_team_1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping("/@{username}/feed")
    public List<TweetResponseDto> getUserFeed(@PathVariable("username") String username) {
        return userService.getFeedByUsername(username);
    }
}
