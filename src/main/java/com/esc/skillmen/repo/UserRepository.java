package com.esc.skillmen.repo;

import com.esc.skillmen.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {


    Optional<User> findByNumber(String number);
}
