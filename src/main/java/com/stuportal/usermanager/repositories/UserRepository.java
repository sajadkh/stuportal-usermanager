package com.stuportal.usermanager.repositories;

import com.stuportal.usermanager.models.Role;
import org.springframework.data.repository.CrudRepository;

import com.stuportal.usermanager.models.User;
import java.util.*;

public interface UserRepository extends CrudRepository<User, String> {
    User findByIdNumber(String idNumber);
    List<User> findByRole(Role role);
}