package com.cooksys.group_project_1_team_1.controllers;

import com.cooksys.group_project_1_team_1.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ValidateController {
    private final ValidateService validateService;
}
