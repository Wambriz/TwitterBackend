package com.cooksys.group_project_1_team_1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class Credentials {
    @Column(unique = true,nullable = false)
    private String username;

    private String password;
}
