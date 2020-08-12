package com.stuportal.usermanager.forms.responseForms;

import com.stuportal.usermanager.utilities.ResponseStatus;

import java.io.Serializable;
import java.util.Date;

public class ResponseForm implements Serializable {
    private static final long serialVersionUID = 6961277911480595301L;

    String status;
    Date timestamp;

    public ResponseForm(){
        this.status = ResponseStatus.SUCCESS.name();
        this.timestamp = new Date(System.currentTimeMillis());
    }

    public String getStatus(){
        return this.status;
    }

    public Date getTimestamp(){
        return this.timestamp;
    }
}
