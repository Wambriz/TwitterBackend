package com.cooksys.group_project_1_team_1.services;

import com.cooksys.group_project_1_team_1.models.HashtagDto;
import com.cooksys.group_project_1_team_1.models.TweetResponseDto;

import java.util.List;

public interface HashtagService {
    List<HashtagDto> getAllTags();

    List<TweetResponseDto> getTweetsByTagLabel(String label);
}
