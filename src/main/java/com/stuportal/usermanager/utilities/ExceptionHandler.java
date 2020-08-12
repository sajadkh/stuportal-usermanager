package com.stuportal.usermanager.utilities;

import com.stuportal.usermanager.exceptions.serverExceptions.InternalServerException;
import com.stuportal.usermanager.exceptions.userExceptions.UserException;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandler {
    public Exception handleException(Exception e) {
        if(e instanceof UserException){
            return e;
        }
        else {
            return new InternalServerException();
        }
    }
}
