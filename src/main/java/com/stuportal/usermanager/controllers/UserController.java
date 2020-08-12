package com.stuportal.usermanager.controllers;


//Project Packages

import com.stuportal.usermanager.forms.requesetForms.ChangePasswordRequestForm;
import com.stuportal.usermanager.forms.requesetForms.ForgetPasswordRequestForm;
import com.stuportal.usermanager.forms.requesetForms.LoginRequestForm;
import com.stuportal.usermanager.forms.requesetForms.SignUpRequestForm;
import com.stuportal.usermanager.forms.responseForms.*;
import com.stuportal.usermanager.models.User;
import com.stuportal.usermanager.services.UserManagerService;

//Spring Packages
import com.stuportal.usermanager.utilities.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;


//Java Packages
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserManagerService userManagerService;

    @Autowired
    private ExceptionHandler exceptionHandler;

    @PutMapping("/login")
    public LoginResponseForm login(@Valid @RequestBody LoginRequestForm loginRequestForm) throws Exception {
        try {
            return new LoginResponseForm(userManagerService.login(loginRequestForm.getUserId(), loginRequestForm.getPassword()));
        } catch (Exception e) {
            throw exceptionHandler.handleException(e);
        }
    }

    @PostMapping("/signUp")
    public SignUpResponseForm singUp(@Valid @RequestBody SignUpRequestForm signUpRequestForm) throws Exception {
        try {
            Map<String, String> userInfo = userManagerService.singUp(signUpRequestForm);
            return new SignUpResponseForm(userInfo.get("id"), userInfo.get("pass"));
        } catch (Exception e) {
            throw exceptionHandler.handleException(e);
        }
    }

    @GetMapping("/user")
    public List<User> getUser(@RequestParam(required = false) String userId, @RequestParam(required = false) String role,
                              @RequestHeader HttpHeaders headers) throws Exception {
        try {
            userManagerService.hasAccess(headers.getFirst(HttpHeaders.AUTHORIZATION), new Throwable().getStackTrace()[0]
                    .getMethodName());
            if (userId != null) {
                List<User> users = new ArrayList<>();
                users.add(userManagerService.getUserById(userId));
                return users;
            } else if (role != null) {
                return userManagerService.getUsersByRole(role);
            } else {
                return userManagerService.getAllUsers();
            }
        } catch (Exception e) {
            throw exceptionHandler.handleException(e);
        }
    }

    @DeleteMapping("/user")
    public ResponseForm deleteUser(@RequestHeader HttpHeaders headers) throws Exception {
        try {
            Map<String, String> userInfo = userManagerService.verifyToken(headers.getFirst(HttpHeaders.AUTHORIZATION));
            userManagerService.deleteUser(userInfo.get("userId"));
            return new ResponseForm();
        } catch (Exception e) {
            throw exceptionHandler.handleException(e);
        }
    }


    @PostMapping("/verifyToken")
    public VerifyTokenResponseForm verifyToken(@RequestHeader HttpHeaders headers) {
        Map<String, String> map = userManagerService.verifyToken(headers.getFirst(HttpHeaders.AUTHORIZATION));
        return new VerifyTokenResponseForm(map.get("userId"), map.get("role"), true);
    }

    @PutMapping("/changePassword")
    public ResponseForm changePassword(@RequestHeader HttpHeaders headers,
                                       @Valid @RequestBody ChangePasswordRequestForm changePasswordRequestForm) throws Exception {
        try {
            userManagerService.hasAccess(headers.getFirst(HttpHeaders.AUTHORIZATION), new Throwable().getStackTrace()[0]
                    .getMethodName());
            Map<String, String> userInfo = userManagerService.verifyToken(headers.getFirst(HttpHeaders.AUTHORIZATION));
            userManagerService.changePassword(userInfo.get("userId"), changePasswordRequestForm.getPreviousPassword(),
                    changePasswordRequestForm.getNewPassword());
            return new ResponseForm();

        } catch (Exception e) {
            throw exceptionHandler.handleException(e);
        }
    }

    @GetMapping("/hasAccess")
    public HasAccessResponseForm hasAccess(@RequestParam String resourceName,
                                           @RequestHeader HttpHeaders headers) {
        try {
            userManagerService.hasAccess(headers.getFirst(HttpHeaders.AUTHORIZATION), resourceName);
            return new HasAccessResponseForm(true);
        } catch (Exception e) {
            return new HasAccessResponseForm(false);
        }
    }

    @PutMapping("/forgetPassword")
    public ResponseForm forgetPassword(@Valid @RequestBody ForgetPasswordRequestForm forgetPasswordRequestForm) {
        try {
            return new ForgetPasswordResponseForm(userManagerService.forgetPassword(forgetPasswordRequestForm.getUserId()));
        } catch (Exception e) {
            return new HasAccessResponseForm(false);
        }
    }
}
