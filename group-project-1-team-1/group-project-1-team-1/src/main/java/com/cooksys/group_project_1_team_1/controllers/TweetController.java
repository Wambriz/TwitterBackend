package com.cooksys.group_project_1_team_1.controllers;

import com.cooksys.group_project_1_team_1.models.*;
import com.cooksys.group_project_1_team_1.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.createTweet(tweetRequestDto);
    }

    @PostMapping("/{id}/repost")
    public TweetResponseDto repostTweet(@PathVariable Long id, @RequestBody CredentialDto credentialDto) {
        return tweetService.repostTweet(id, credentialDto);
    }

    @GetMapping
    public List<TweetResponseDto> getAllTweets() {
        return tweetService.getAllTweets();
    }

    @GetMapping("/{id}")
    public TweetResponseDto getTweet(@PathVariable Long id) {
        return tweetService.getTweet(id);
    }

    @GetMapping("/{id}/tags")
    public List<HashtagDto> getTweetHashtags(@PathVariable Long id) {
        return tweetService.getTweetHashtags(id);
    }

    @GetMapping("/{id}/likes")
    public List<UserResponseDto> getTweetLikes(@PathVariable Long id) {
        return tweetService.getTweetLikes(id);
    }

    @GetMapping("/{id}/reposts")
    public List<TweetResponseDto> getTweetReposts(@PathVariable("id") Long id) {
        return tweetService.getTweetReposts(id);
    }

    @GetMapping("/{id}/context")
    public ContextDto getTweetContext(@PathVariable("id") Long id) {
        return tweetService.getTweetContext(id);
    }

    @GetMapping("/{id}/mentions")
    public ResponseEntity<List<UserResponseDto>> getTweetMentions(@PathVariable Long id) {
        List<UserResponseDto> mentions = (List<UserResponseDto>) tweetService.getTweetMentions(id);
        return ResponseEntity.ok(mentions);
    }

    @GetMapping("/{id}/replies")
    public ResponseEntity<List<TweetResponseDto>> getTweetReplies(@PathVariable Long id) {
        List<TweetResponseDto> replies = tweetService.getTweetReplies(id);
        return ResponseEntity.ok(replies);
    }
}