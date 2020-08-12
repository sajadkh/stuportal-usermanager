package com.stuportal.usermanager.forms.responseForms;

public class SignUpResponseForm extends ResponseForm{
    private static final long serialVersionUID = -2829592453142729822L;

    private String password;
    private String userId;

    public SignUpResponseForm(String userId, String password){
        this.password = password;
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public String getUserId() {
        return userId;
    }
}
