package com.cooksys.group_project_1_team_1.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@AllArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 257061055131577313L;

    private String message;
}
