package com.cooksys.group_project_1_team_1.services.impl;

import com.cooksys.group_project_1_team_1.repositories.HashtagRepository;
import com.cooksys.group_project_1_team_1.repositories.UserRepository;
import com.cooksys.group_project_1_team_1.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {
    private final HashtagRepository hashtagRepository;
    private final UserRepository userRepository;

    @Override
    public boolean validateHashtagExists(String label) {
        return hashtagRepository.findByLabelIgnoreCase(label)!=null;

    }

    @Override
    public boolean validateUsernameExists(String username) {
        return userRepository.findByCredentialsUsername(username)!=null;
    }

    @Override
    public boolean validateUsernameAvailability(String username) {

        return userRepository.findByCredentialsUsername(username)==null;
    }
}
