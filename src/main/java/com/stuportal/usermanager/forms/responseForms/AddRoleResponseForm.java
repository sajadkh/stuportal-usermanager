package com.stuportal.usermanager.forms.responseForms;

public class AddRoleResponseForm extends ResponseForm{
    private static final long serialVersionUID = -389716078610284306L;

    private int id;

    public AddRoleResponseForm(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
