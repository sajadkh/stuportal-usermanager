package com.stuportal.usermanager.forms.requesetForms;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChangePasswordRequestForm extends RequestForm {
    private static final long serialVersionUID = -2415698368071547445L;

    @NotNull
    @Size(min=5)
    private String previousPassword;
    @NotNull
    @Size(min=5)
    private String newPassword;

    public ChangePasswordRequestForm(String previousPassword, String newPassword) {
        this.previousPassword = previousPassword;
        this.newPassword = newPassword;
    }

    public String getPreviousPassword() {
        return previousPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
