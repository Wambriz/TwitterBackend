package com.cooksys.group_project_1_team_1.controllers;

import com.cooksys.group_project_1_team_1.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagsController {
    private final HashtagService hashtagService;
}
