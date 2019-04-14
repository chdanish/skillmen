package com.esc.basic.api;

import com.esc.basic.domain.User;
import com.esc.basic.model.UserModel;
import com.esc.basic.service.Impl.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/user")
@Api(description = "Set of endpoints for Creating, Retrieving, Updating and Deleting of Persons.")
public class UserController {

    private UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/principal")
    @ApiOperation("Return principal user.")
    public Principal getPrincipal(@ApiIgnore Principal principal){
        log.debug("Request for principal. ",principal);
        return principal;
    }

    @GetMapping
    public Optional<User> getUser(@ApiIgnore Principal principal) {
        return userService.getRepository().findByNumber(principal.getName());
    }

    @GetMapping("/id")
    public Optional<User> getUser(@ApiIgnore Principal principal, @RequestParam String id) {
        return userService.get(id);
    }

    @GetMapping("/all")
    public Optional<List<User>> getAllUser(@ApiIgnore Principal principal, @RequestParam String id) {
        return userService.getAll();
    }

    @PostMapping
    public Optional<User> saveUser(@ApiIgnore Principal principal, @RequestBody UserModel userModel) {
        log.debug("Recieved new user save request: ",userModel);
        return userService.save(userModel.toUser());
    }

    @PostMapping("/all")
    public Optional<List<User>> saveUser(@ApiIgnore Principal principal, @RequestBody List<UserModel> userModel) {
        log.debug("Recieved new user save request: ",userModel);
        List<User> users = userModel.stream().filter(u -> u.getId() == null)
                .peek(log::debug).flatMap(userModel1 -> userModel.stream())
                .map(userModel1 -> userModel1.toUser()).collect(Collectors.toList());
        return userService.saveAll(users);
    }

    @PutMapping("/password")
    public Optional<User> updateUserPassword(@ApiIgnore Principal principal, @RequestBody UserModel userModel) {
        log.debug("Recieved put request for user: ",userModel);
        User user = userService.get(userModel.getId())
                .orElseThrow(() -> new RuntimeException("Entity not found exception"));
        user.setPassword(userModel.getPassword());
        return userService.save(user);
    }


}
