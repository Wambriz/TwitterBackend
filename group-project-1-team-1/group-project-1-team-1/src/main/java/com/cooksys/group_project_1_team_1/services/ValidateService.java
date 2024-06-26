package com.cooksys.group_project_1_team_1.services;

public interface ValidateService {
    boolean validateHashtagExists(String label);

    boolean validateUsernameExists(String username);

    boolean validateUsernameAvailability(String username);
}
