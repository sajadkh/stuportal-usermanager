package com.stuportal.usermanager.exceptions.userExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Duplicate Role!")
public class DuplicateRoleException extends UserException {}
