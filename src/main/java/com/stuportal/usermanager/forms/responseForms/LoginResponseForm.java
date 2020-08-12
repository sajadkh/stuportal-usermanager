package com.stuportal.usermanager.forms.responseForms;

public class LoginResponseForm extends ResponseForm {
    private static final long serialVersionUID = -1680437756826265391L;
    private String jwtToken;

    public LoginResponseForm(String jwtToken){
        this.jwtToken = jwtToken;
    }
    public String getToken() {
        return this.jwtToken;
    }
}
