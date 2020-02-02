package com.esc.skillmen.repo;

import com.esc.skillmen.domain.Role;
import com.esc.skillmen.domain.UserRoles;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRolesRepository extends CrudRepository<UserRoles, String> {
	
	@Query("SELECT ur FROM UserRoles ur where  ur.user.id = ?1")
	List<UserRoles> findByUserId(String userId);
	

}
