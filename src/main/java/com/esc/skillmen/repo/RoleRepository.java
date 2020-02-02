package com.esc.skillmen.repo;

import com.esc.skillmen.domain.Role;
import com.esc.skillmen.domain.UserRoles;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends CrudRepository<Role, String> {


    Optional<Role> findByName(String name);
    
    
}
