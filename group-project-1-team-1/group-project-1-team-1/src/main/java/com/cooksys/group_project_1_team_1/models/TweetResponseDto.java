package com.cooksys.group_project_1_team_1.models;

import com.cooksys.group_project_1_team_1.entities.Tweet;
import com.cooksys.group_project_1_team_1.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class TweetResponseDto {

    private Long id;

    private User author;

    private Timestamp posted;

    private String content;

    private Tweet inReplyTo;

    private Tweet repostOf;

}
