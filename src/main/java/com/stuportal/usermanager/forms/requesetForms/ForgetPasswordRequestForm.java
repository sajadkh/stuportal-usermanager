package com.stuportal.usermanager.forms.requesetForms;

import javax.validation.constraints.NotNull;

public class ForgetPasswordRequestForm extends RequestForm{
    private static final long serialVersionUID = -6434333710897930008L;

    @NotNull
    private String userId;

    public String getUserId() {
        return userId;
    }
}
