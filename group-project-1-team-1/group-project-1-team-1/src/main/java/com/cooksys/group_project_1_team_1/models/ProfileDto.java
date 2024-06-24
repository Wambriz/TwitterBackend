package com.cooksys.group_project_1_team_1.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProfileDto {
    private String firstName;

    private String lastName;

    private String email;

    private String phone;
}
