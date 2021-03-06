package com.esc.skillmen.repo;

import com.esc.skillmen.domain.Role;
import com.esc.skillmen.domain.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {


    Optional<User> findByNumber(String number);
    
    @Query("select u from User u")
    List<User> findAllUsers();
}
