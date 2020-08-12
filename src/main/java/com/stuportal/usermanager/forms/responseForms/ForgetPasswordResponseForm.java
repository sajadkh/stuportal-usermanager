package com.stuportal.usermanager.forms.responseForms;

public class ForgetPasswordResponseForm extends ResponseForm{
    private static final long serialVersionUID = 4135169377014651307L;

    private String password;

    public ForgetPasswordResponseForm(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
