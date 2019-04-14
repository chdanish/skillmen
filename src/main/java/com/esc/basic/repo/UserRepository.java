package com.esc.basic.repo;

import com.esc.basic.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {


    Optional<User> findByNumber(String number);
}
