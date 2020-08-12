package com.stuportal.usermanager.forms.responseForms;

public class HasAccessResponseForm extends ResponseForm{
    private static final long serialVersionUID = -4283931722042722277L;

    private Boolean result;

    public HasAccessResponseForm(Boolean result) {
        this.result = result;
    }

    public Boolean getResult() {
        return result;
    }
}
