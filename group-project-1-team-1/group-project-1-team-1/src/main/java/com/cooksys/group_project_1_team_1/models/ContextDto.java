package com.cooksys.group_project_1_team_1.models;

import com.cooksys.group_project_1_team_1.entities.Tweet;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ContextDto {

    private Tweet target;

    private List<Tweet> before;

    private List<Tweet> after;

}
