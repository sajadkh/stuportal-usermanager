package com.stuportal.usermanager.forms.responseForms;

public class VerifyTokenResponseForm extends ResponseForm{
    private static final long serialVersionUID = -3812001283452273561L;

    private String userId;
    private String role;
    private Boolean isTokenValid;

    public VerifyTokenResponseForm(String userId, String role, Boolean isTokenValid){
        this.userId = userId;
        this.role = role;
        this.isTokenValid = isTokenValid;
    }

    public String getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public Boolean getTokenStatus() {
        return isTokenValid;
    }
}
