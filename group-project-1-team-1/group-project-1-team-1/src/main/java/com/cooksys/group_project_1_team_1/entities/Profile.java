package com.cooksys.group_project_1_team_1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class Profile {
    private String firstName;

    private String lastName;

    @Column(nullable = false)
    private String email;

    private String phone;
}
