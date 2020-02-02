package com.esc.skillmen;

import com.esc.skillmen.config.RolesUtil;
import com.esc.skillmen.config.WebConfig;
import com.esc.skillmen.domain.Role;
import com.esc.skillmen.domain.User;
import com.esc.skillmen.domain.UserRoles;
import com.esc.skillmen.repo.RoleRepository;
import com.esc.skillmen.repo.UserRolesRepository;
import com.esc.skillmen.service.Impl.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@SpringBootApplication
public class SkillmenApplication {

	public static void main(String[] args) {
		SpringApplication sp = new SpringApplication(SkillmenApplication.class, WebConfig.class);
		sp.run("--debug");
	}

}


@Component
class DataWriter implements ApplicationRunner {

	private UserService userService;
	private RoleRepository roleRepository;
	private UserRolesRepository userRolesRepository;
	private RolesUtil rolesUtil;

	public DataWriter(UserService userService, RoleRepository roleRepository, UserRolesRepository userRolesRepository, RolesUtil rolesUtil) {
		this.userService = userService;
		this.roleRepository = roleRepository;
		this.userRolesRepository = userRolesRepository;
		this.rolesUtil = rolesUtil;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		Role userRole = rolesUtil.getRoleByName("ROLE_USER");
		Role adminRole = rolesUtil.getRoleByName("ROLE_ADMIN");
		User user1 = userService.save(new User(null,"923410000909","923410000909", new HashSet<UserRoles>()))
				.orElseThrow(() -> new RuntimeException("Failed to find user."));
		User user2 = userService.save(new User(null,"admin","admin", new HashSet<UserRoles>()))
				.orElseThrow(() -> new RuntimeException("Failed to find user."));
		UserRoles userRoles1 = userRolesRepository.save(new UserRoles(null,user1,userRole));
		UserRoles userRoles2 = userRolesRepository.save(new UserRoles(null,user2,adminRole));

	}
}

