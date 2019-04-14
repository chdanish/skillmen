package com.esc.basic;

import com.esc.basic.config.WebConfig;
import com.esc.basic.constant.Role;
import com.esc.basic.domain.User;
import com.esc.basic.service.Impl.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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

	public DataWriter(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		Arrays.asList("923410000909","923410000900","admin")
				.stream()
				.forEach(num -> userService.save(new User(null,num,num, new HashSet<>(Arrays.asList(Role.Role_User)))));

	}
}