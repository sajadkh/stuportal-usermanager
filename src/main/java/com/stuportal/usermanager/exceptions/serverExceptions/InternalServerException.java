package com.stuportal.usermanager.exceptions.serverExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal Server Error!")
public class InternalServerException extends ServerException{}
