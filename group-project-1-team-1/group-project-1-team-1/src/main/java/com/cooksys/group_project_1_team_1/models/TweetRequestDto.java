package com.cooksys.group_project_1_team_1.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TweetRequestDto {
    private String content;

    private CredentialDto credentials;
}
