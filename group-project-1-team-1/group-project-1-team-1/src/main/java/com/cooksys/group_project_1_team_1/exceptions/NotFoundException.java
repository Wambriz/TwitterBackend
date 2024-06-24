package com.cooksys.group_project_1_team_1.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@AllArgsConstructor
@Getter
@Setter
public class NotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -7273735898491754397L;

    private String message;
}
