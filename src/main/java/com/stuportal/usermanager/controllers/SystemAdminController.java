package com.stuportal.usermanager.controllers;


//Project Packages

import com.stuportal.usermanager.exceptions.serverExceptions.InternalServerException;
import com.stuportal.usermanager.exceptions.userExceptions.RoleNotFoundException;
import com.stuportal.usermanager.forms.requesetForms.*;
import com.stuportal.usermanager.forms.responseForms.ResponseForm;
import com.stuportal.usermanager.models.Resource;
import com.stuportal.usermanager.services.AccessManagerService;
import com.stuportal.usermanager.services.UserManagerService;
import com.stuportal.usermanager.utilities.ExceptionHandler;
import com.stuportal.usermanager.models.Role;
import com.stuportal.usermanager.services.RoleManagerService;

//Spring Packages
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;


//Java Packages
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SystemAdminController {

    @Autowired
    private RoleManagerService roleManagerService;

    @Autowired
    private UserManagerService userManagerService;

    @Autowired
    private AccessManagerService accessManagerService;

    @Autowired
    private ExceptionHandler exceptionHandler;

    @Value("${stuPortal.sysAdminRole}")
    private String sysAdminRole;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        try{
            roleManagerService.getRole(this.sysAdminRole);
        }catch (RoleNotFoundException e){
            roleManagerService.addRole(this.sysAdminRole);
        }
    }

    @PostMapping("/role")
    public ResponseForm addRole(@Valid @RequestBody AddRoleRequestForm addRoleRequestForm,
                                @RequestHeader HttpHeaders headers) throws Exception{
        try {
            userManagerService.hasAccess(headers.getFirst(HttpHeaders.AUTHORIZATION), new Throwable().getStackTrace()[0]
                    .getMethodName());
            roleManagerService.addRole(addRoleRequestForm.getName());
            return new ResponseForm();
        }catch (Exception e){
            throw exceptionHandler.handleException(e);
        }
    }

    @GetMapping("/role")
    public List<Role> getRoles(@RequestParam(required = false) String name, @RequestHeader HttpHeaders headers)
            throws Exception{
        try {
            userManagerService.hasAccess(headers.getFirst(HttpHeaders.AUTHORIZATION), new Throwable().getStackTrace()[0]
                    .getMethodName());
            List<Role> roles = new ArrayList<>();
            if (name != null) {
                roles.add(roleManagerService.getRole(name));
            } else {
                roles = roleManagerService.getRoles();
            }
            return roles;
        }catch (Exception e){
            throw exceptionHandler.handleException(e);
        }
    }

    @DeleteMapping("/role")
    public ResponseForm deleteRole(@Valid @RequestBody DeleteRoleRequestForm deleteRoleRequestForm,
                              @RequestHeader HttpHeaders headers) throws Exception{
        try {
            userManagerService.hasAccess(headers.getFirst(HttpHeaders.AUTHORIZATION), new Throwable().getStackTrace()[0]
                    .getMethodName());
            roleManagerService.deleteRole(deleteRoleRequestForm.getName());
            return new ResponseForm();
        }catch (Exception e){
            throw exceptionHandler.handleException(e);
        }
    }

    @PostMapping("/resource")
    public ResponseForm addResource(@Valid @RequestBody AddResourceRequestForm addResourceRequestForm,
                                    @RequestHeader HttpHeaders headers) throws Exception{
        try {
            userManagerService.hasAccess(headers.getFirst(HttpHeaders.AUTHORIZATION), new Throwable().getStackTrace()[0]
                    .getMethodName());
            accessManagerService.addResource(addResourceRequestForm.getName());
            return new ResponseForm();
        }catch (Exception e){
            throw exceptionHandler.handleException(e);
        }
    }

    @GetMapping("/resource")
    public List<Resource> getResource(@RequestParam(required = false) String name,
                                      @RequestHeader HttpHeaders headers)
            throws Exception{
        try {
            userManagerService.hasAccess(headers.getFirst(HttpHeaders.AUTHORIZATION), new Throwable().getStackTrace()[0]
                    .getMethodName());
            List<Resource> resources = new ArrayList<>();
            if (name != null) {
                resources.add(accessManagerService.getResource(name));
            } else {
                resources = accessManagerService.getResources();
            }
            return resources;
        }catch (Exception e){
            throw exceptionHandler.handleException(e);
        }
    }

    @DeleteMapping("/resource")
    public ResponseForm deleteResource(@Valid @RequestBody DeleteResourceRequestForm deleteResourceRequestForm,
                                   @RequestHeader HttpHeaders headers) throws Exception{
        try {
            userManagerService.hasAccess(headers.getFirst(HttpHeaders.AUTHORIZATION), new Throwable().getStackTrace()[0]
                    .getMethodName());
            accessManagerService.deleteResource(deleteResourceRequestForm.getName());
            return new ResponseForm();
        }catch (Exception e){
            throw exceptionHandler.handleException(e);
        }
    }

    @PostMapping("/access")
    public ResponseForm addAccess(@RequestHeader HttpHeaders headers,
                                      @Valid @RequestBody AddAccessRequestForm addAccessRequestForm) throws Exception{
        try {
            userManagerService.hasAccess(headers.getFirst(HttpHeaders.AUTHORIZATION), new Throwable().getStackTrace()[0]
                    .getMethodName());
            accessManagerService.addAccessToResource(addAccessRequestForm.getRoleName(),
                    addAccessRequestForm.getResourceName());
            return new ResponseForm();
        }catch (Exception e){
            throw exceptionHandler.handleException(e);
        }
    }

    @DeleteMapping("/access")
    public ResponseForm addAccess(@RequestHeader HttpHeaders headers,
                                  @Valid @RequestBody DeleteAccessRequestForm deleteAccessRequestForm) throws Exception{
        try {
            userManagerService.hasAccess(headers.getFirst(HttpHeaders.AUTHORIZATION), new Throwable().getStackTrace()[0]
                    .getMethodName());
            accessManagerService.deleteAccessToResource(deleteAccessRequestForm.getRoleName(),
                    deleteAccessRequestForm.getResourceName());
            return new ResponseForm();
        }catch (Exception e){
            throw exceptionHandler.handleException(e);
        }
    }

}
