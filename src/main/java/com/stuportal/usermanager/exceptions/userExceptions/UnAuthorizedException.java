package com.stuportal.usermanager.exceptions.userExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason="UnAuthorized User!")

public class UnAuthorizedException extends UserException {}
