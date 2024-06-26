package com.cooksys.group_project_1_team_1.controllers;

import com.cooksys.group_project_1_team_1.models.HashtagDto;
import com.cooksys.group_project_1_team_1.models.TweetRequestDto;
import com.cooksys.group_project_1_team_1.models.TweetResponseDto;
import com.cooksys.group_project_1_team_1.models.UserResponseDto;
import com.cooksys.group_project_1_team_1.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto){
        return tweetService.createTweet(tweetRequestDto);
    }

    @GetMapping
    public List<TweetResponseDto> getAllTweets(){
        return tweetService.getAllTweets();
    }

    @GetMapping("/{id}")
    public TweetResponseDto getTweet(@PathVariable Long id){
        return tweetService.getTweet(id);
    }

    @GetMapping("/{id}/tags")
    public List<HashtagDto> getTweetHashtags(@PathVariable Long id){
        return tweetService.getTweetHashtags(id);
    }

    @GetMapping("/{id}/likes")
    public List<UserResponseDto> getTweetLikes(@PathVariable Long id){
        return tweetService.getTweetLikes(id);
    }

    @GetMapping("/{id}/reposts")
    public List<TweetResponseDto> getTweetReposts(@PathVariable("id") Long id) {
        return tweetService.getTweetReposts(id);
    }



}
