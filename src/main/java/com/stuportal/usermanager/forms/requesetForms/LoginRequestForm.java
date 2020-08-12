package com.stuportal.usermanager.forms.requesetForms;

import javax.validation.constraints.*;

public class LoginRequestForm extends RequestForm {
    private static final long serialVersionUID = 4690875934527256928L;

    @NotNull
    private String userId;
    @NotNull
    private String password;

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
