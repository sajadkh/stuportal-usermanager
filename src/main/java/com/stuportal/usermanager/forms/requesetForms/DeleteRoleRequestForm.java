package com.stuportal.usermanager.forms.requesetForms;

import javax.validation.constraints.NotNull;

public class DeleteRoleRequestForm extends RequestForm {
    private static final long serialVersionUID = 5929582663597299409L;

    @NotNull
    private String name;

    public String getName() {
        return name;
    }
}
