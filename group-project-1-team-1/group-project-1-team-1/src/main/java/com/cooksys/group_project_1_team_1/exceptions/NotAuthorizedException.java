package com.cooksys.group_project_1_team_1.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@AllArgsConstructor
@Getter
@Setter
public class NotAuthorizedException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -5014409485698015315L;

    private String message;

}
