package com.stuportal.usermanager.repositories;

import com.stuportal.usermanager.models.Resource;
import org.springframework.data.repository.CrudRepository;

public interface ResourceRepository extends CrudRepository<Resource, String> {
}