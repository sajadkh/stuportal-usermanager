package com.stuportal.usermanager.forms.requesetForms;

import javax.validation.constraints.NotNull;

public class AddResourceRequestForm extends RequestForm{
    private static final long serialVersionUID = -457151589255261301L;

    @NotNull
    private String name;

    public String getName() {
        return name;
    }
}
