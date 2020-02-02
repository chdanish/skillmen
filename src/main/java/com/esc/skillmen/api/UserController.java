package com.esc.skillmen.api;

import com.esc.skillmen.Exception.RecordNotFoundException;
import com.esc.skillmen.domain.Role;
import com.esc.skillmen.domain.User;
import com.esc.skillmen.model.UserModel;
import com.esc.skillmen.repo.RoleRepository;
import com.esc.skillmen.repo.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/user")
@Api(description = "Set of endpoints for Creating, Retrieving, Updating and Deleting of Persons.")
public class UserController {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserController(final UserRepository userRepository, final RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/principal")
    @ApiOperation("Return principal user.")
    public Principal getPrincipal(@ApiIgnore Principal principal){
        log.debug("Request for principal. ",principal);
        return principal;
    }

    @GetMapping
    public Optional<User> getUser(@ApiIgnore Principal principal) {
        return userRepository.findByNumber(principal.getName());
    }

    @GetMapping("/id")
    public User getUser(@ApiIgnore Principal principal, @RequestParam String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException( "Entity not found." ));
    }

    @GetMapping("/all")
    public Iterable<User> getAllUser(@ApiIgnore Principal principal) {
        return userRepository.findAllUsers();
    }

    @PostMapping
    public User saveUser(@ApiIgnore Principal principal, @RequestBody UserModel userModel) {
        log.debug("Recieved new user save request: ",userModel);
        final User save = userRepository.save(userModel.toUser());
        final Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RecordNotFoundException("Role not found with name 'USER'"));
        return save;
    }



}
