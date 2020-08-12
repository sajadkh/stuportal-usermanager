package com.stuportal.usermanager.forms.requesetForms;

import javax.validation.constraints.NotNull;

public class AddRoleRequestForm extends RequestForm {
    private static final long serialVersionUID = 7369609328374522402L;

    @NotNull
    private String name;

    public String getName() {
        return name;
    }
}
