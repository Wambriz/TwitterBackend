package com.cooksys.group_project_1_team_1.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRequestDto {
    private CredentialDto credentials;

    private ProfileDto profile;
}
