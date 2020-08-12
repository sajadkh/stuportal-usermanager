package com.stuportal.usermanager.exceptions.userExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="There are some active user with this role!")
public class ActiveUserWithRoleException extends UserException{
}
