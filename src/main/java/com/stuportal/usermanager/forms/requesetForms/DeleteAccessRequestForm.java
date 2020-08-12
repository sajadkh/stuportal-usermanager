package com.stuportal.usermanager.forms.requesetForms;

import javax.validation.constraints.NotNull;

public class DeleteAccessRequestForm extends RequestForm{
    private static final long serialVersionUID = -457151589255261301L;

    @NotNull
    private String roleName;
    @NotNull
    private String resourceName;

    public String getRoleName() {
        return roleName;
    }

    public String getResourceName() {
        return resourceName;
    }
}
