package com.stuportal.usermanager.forms.requesetForms;

import com.stuportal.usermanager.forms.requesetForms.RequestForm;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignUpRequestForm extends RequestForm {
    private static final long serialVersionUID = 8435800601475271451L;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String fatherName;

    @NotNull
    @Size(min=10, max=10)
    private String idNumber;

    @NotNull
    private String role;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 11, max = 11)
    @Pattern(regexp = "09\\d{9}$")
    private String phoneNumber;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
