package com.esc.basic.api;

import com.esc.basic.repo.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;

@RestController
@RequestMapping("/skill")
@Api(description = "Set of endpoints for Creating, Retrieving, Updating and Deleting of Persons.")
public class SkillController {

    private UserRepository userRepository;

    public SkillController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @ApiOperation("Return principal user.")
    public Principal getPrincipal(@ApiIgnore Principal principal){
        return principal;
    }

}
