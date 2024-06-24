package com.cooksys.group_project_1_team_1.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class UserResponseDto {
    private String username;

    private ProfileDto profile;

    private Timestamp joined;
}
