package com.cooksys.group_project_1_team_1.controllers;

import com.cooksys.group_project_1_team_1.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {
    private final ValidateService validateService;

    @GetMapping("/tag/exists/{label}")
    public boolean validateHashtagExists(@PathVariable("label") String label){
        return validateService.validateHashtagExists(label);
    }

    @GetMapping("/username/exists/@{username}")
    public boolean validateUsernameExists(@PathVariable("username") String username) {
        return validateService.validateUsernameExists(username);
    }

    @GetMapping("/username/available/@{username}")
    public boolean validateUsernameAvailability(@PathVariable("username") String username) {
        return validateService.validateUsernameAvailability(username);
    }
}
