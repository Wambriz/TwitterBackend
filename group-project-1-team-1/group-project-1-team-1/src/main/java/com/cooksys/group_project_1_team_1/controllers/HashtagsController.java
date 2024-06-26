package com.cooksys.group_project_1_team_1.controllers;

import com.cooksys.group_project_1_team_1.models.HashtagDto;
import com.cooksys.group_project_1_team_1.models.TweetResponseDto;
import com.cooksys.group_project_1_team_1.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagsController {
    private final HashtagService hashtagService;

    @GetMapping
    public List<HashtagDto> getAllHashtags(){
        return hashtagService.getAllTags();
    }

    @GetMapping("/{label}")
    public List<TweetResponseDto> getTweetsByTagLabel(@PathVariable("label") String label){
        return hashtagService.getTweetsByTagLabel(label);
    }
}
