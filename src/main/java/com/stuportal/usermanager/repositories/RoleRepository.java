package com.stuportal.usermanager.repositories;

import org.springframework.data.repository.CrudRepository;

import com.stuportal.usermanager.models.Role;

public interface RoleRepository extends CrudRepository<Role, String> {
}