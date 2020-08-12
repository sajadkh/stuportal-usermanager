package com.stuportal.usermanager.services;

import com.stuportal.usermanager.exceptions.serverExceptions.DBException;
import com.stuportal.usermanager.exceptions.userExceptions.DuplicateResourceException;
import com.stuportal.usermanager.exceptions.userExceptions.ResourceNotFoundException;
import com.stuportal.usermanager.exceptions.userExceptions.RoleNotFoundException;
import com.stuportal.usermanager.models.Resource;
import com.stuportal.usermanager.models.Role;
import com.stuportal.usermanager.repositories.ResourceRepository;
import com.stuportal.usermanager.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccessManagerService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleManagerService roleManagerService;

    @Value("${stuPortal.sysAdminRole}")
    private String sysAdminRole;

    private void dbSaveResource(Resource resource) throws DuplicateResourceException, DBException {
        try {
            this.dbGetResource(resource.getName());
            throw new DuplicateResourceException();
        }catch (ResourceNotFoundException e){
            resourceRepository.save(resource);
        } catch (Exception err) {
            throw new DBException();
        }
    }

    private Resource dbGetResource(String name) throws ResourceNotFoundException {
        Optional<Resource> r = resourceRepository.findById(name);
        if (r.isPresent()) {
            return r.get();
        } else {
            throw new ResourceNotFoundException();
        }
    }

    public void addResource(String resourceName) throws DuplicateResourceException, DBException{
        Resource resource = new Resource(resourceName);
        this.dbSaveResource(resource);
    }

    public List<Resource> getResources() throws DBException{
        List<Resource> resources = new ArrayList<>();
        try {
            resourceRepository.findAll().forEach(resources::add);
            return resources;
        }catch (Exception e) {
            throw new DBException();
        }
    }

    public Resource getResource(String resourceName) throws ResourceNotFoundException, DBException{
        Optional<Resource> resource = resourceRepository.findById(resourceName);
        if (resource.isPresent()) {
            return resource.get();
        } else {
            throw new ResourceNotFoundException();
        }
    }

    public void deleteResource(String resourceName) throws ResourceNotFoundException, DBException{
        Resource resource = this.getResource(resourceName);
        try {
            resourceRepository.delete(resource);
        } catch (Exception err) {
            throw new DBException();
        }
    }

    public void addAccessToResource(String roleName, String resourceName) throws  ResourceNotFoundException,
            RoleNotFoundException, DBException {
        Role role = roleManagerService.getRole(roleName);
        Resource resource = this.getResource(resourceName);
        role.addResource(resource);
        try {
            roleRepository.save(role);
            resourceRepository.save(resource);
        }
        catch(Exception e){
            throw new DBException();
        }
    }

    public void deleteAccessToResource(String roleName, String resourceName) throws ResourceNotFoundException,
            RoleNotFoundException, DBException {
        Role role = roleManagerService.getRole(roleName);
        Resource resource = this.getResource(resourceName);
        role.deleteResource(resource);
        try {
            roleRepository.save(role);
            resourceRepository.save(resource);
        }
        catch(Exception e){
            throw new DBException();
        }
    }

    public boolean hasAccess(String roleName, String resourceValue) throws RoleNotFoundException,
            ResourceNotFoundException, DBException{
        if(this.sysAdminRole.equals(roleName)){
            return true;
        }
        Role role = roleManagerService.getRole(roleName);
        Resource resource = this.getResource(resourceValue);
        return role.getResources().contains(resource);
    }
}

