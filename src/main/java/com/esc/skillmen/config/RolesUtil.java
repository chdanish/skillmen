package com.esc.skillmen.config;

import com.esc.skillmen.Exception.RecordNotFoundException;
import com.esc.skillmen.domain.Role;
import com.esc.skillmen.domain.User;
import com.esc.skillmen.domain.UserRoles;
import com.esc.skillmen.repo.RoleRepository;
import com.esc.skillmen.repo.UserRolesRepository;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class RolesUtil {


    private RoleRepository roleRepository;
    private UserRolesRepository userRoleRepository;

    public RolesUtil(RoleRepository roleRepository, UserRolesRepository userRoleRepository) {
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @PostConstruct
    private void setupRoletable(){
    	Set<Role> roles = new HashSet<Role>();
        this.roleRepository.findAll().forEach(roles::add);
        UserRoleUtil.getRoles().stream().forEach(role ->{
            if(!contains(role,roles)){
            	Role newRole = new Role();
            	newRole.setName(role);
                roles.add(this.roleRepository.save(newRole));
            }
        });
    }

    private boolean contains(String role, Set<Role> roles) {
        for(Role role1 : roles){
            if(role1.getName().equals(role)) return true;
        }
        return false;
    }


    static class UserRoleUtil {

        public static final String USER = "ROLE_USER";

        public static final String ADMIN = "ROLE_ADMIN";


        public static List<String> getRoles(){
            return Collections.unmodifiableList(Arrays.asList(USER,ADMIN));
        }
    }


	public List<Role> getUserRoles(User user) {
		List<UserRoles> userRoles = userRoleRepository.findByUserId(user.getId());
    	List<String> ids = userRoles.stream().map(u -> u.getRole().getId()).collect(Collectors.toList());
    	Iterable<Role> roles = roleRepository.findAllById(ids);
    	List<Role> list = new ArrayList<>();
    	roles.forEach(list::add);
    	return list;
	}

	public Role getRoleByName(String name) {
		return this.roleRepository.findByName(name)
		.orElseThrow(() -> new RecordNotFoundException("No role found with name: "+ name + " ."));
	}
}
