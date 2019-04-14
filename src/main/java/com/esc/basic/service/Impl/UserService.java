package com.esc.basic.service.Impl;

import com.esc.basic.domain.User;
import com.esc.basic.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends GenericCrudServiceImpl<User,String> {


    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }


    public UserRepository getRepository() {
        return this.userRepository;
    }

    @Override
    public Optional<User> update(User user) {
        User optionalUser = get(user.getId())
                .orElseThrow(() -> new RuntimeException("Entity not found with ID:"+ user.getId()));
        user.setNumber(user.getNumber());
        user.setPassword(user.getPassword());
        user.setRoles(user.getRoles());
        return save(user);
    }

}