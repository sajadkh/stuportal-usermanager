package com.stuportal.usermanager.services;

import com.stuportal.usermanager.exceptions.serverExceptions.DBException;
import com.stuportal.usermanager.exceptions.userExceptions.ActiveUserWithRoleException;
import com.stuportal.usermanager.exceptions.userExceptions.DuplicateRoleException;
import com.stuportal.usermanager.exceptions.userExceptions.RoleNotFoundException;
import com.stuportal.usermanager.models.Role;
import com.stuportal.usermanager.models.User;
import com.stuportal.usermanager.repositories.RoleRepository;
import com.stuportal.usermanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleManagerService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private void dbSaveRole(Role role) throws DuplicateRoleException, DBException {
        try {
            Optional<Role> r = roleRepository.findById(role.getName());
            if (r.isPresent()) {
                throw new DuplicateRoleException();
            } else {
                roleRepository.save(role);
            }
        } catch (DuplicateRoleException e) {
            throw e;
        } catch (Exception err) {
            throw new DBException();
        }
    }

    public void addRole(String value) throws DuplicateRoleException, DBException {
        Role role = new Role(value);
        dbSaveRole(role);
    }

    public List<Role> getRoles() throws DBException {
        List<Role> roles = new ArrayList<>();
        try {
            roleRepository.findAll().forEach(roles::add);
            return roles;
        } catch (Exception e) {
            throw new DBException();
        }
    }

    public Role getRole(String roleName) throws RoleNotFoundException, DBException {
        try {
            Optional<Role> role = roleRepository.findById(roleName);
            if (role.isPresent()) {
                return role.get();
            } else {
                throw new RoleNotFoundException();
            }
        } catch (RoleNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DBException();
        }
    }

    public void deleteRole(String roleName) throws RoleNotFoundException, ActiveUserWithRoleException, DBException {
        Role role = this.getRole(roleName);
        try {
            List<User> users = userRepository.findByRole(role);
            if (users.size() == 0) {
                roleRepository.delete(role);
            } else {
                throw new ActiveUserWithRoleException();
            }

        }catch (ActiveUserWithRoleException e){
            throw e;
        } catch (Exception e) {
            throw new DBException();
        }
    }
}
