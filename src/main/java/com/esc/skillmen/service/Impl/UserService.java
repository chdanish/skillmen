package com.esc.skillmen.service.Impl;

import com.esc.skillmen.domain.Role;
import com.esc.skillmen.domain.User;
import com.esc.skillmen.repo.UserRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends GenericCrudServiceImpl<User,String> {


    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        super((CrudRepository)userRepository);
        this.userRepository = userRepository;
    }




    @Override
    public Optional<User> update(User user) {
        User user1 = get(user.getId());
        user1.setPassword(user.getPassword());
        return save(user);
    }

}