package com.esc.skillmen.model;

import com.esc.skillmen.domain.User;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserModel {

    private String id;

    private String number, password;

    private Set<String> roles = new HashSet<>();

    public User toUser(){
        return new User(id,number,password,new HashSet<>());
    }
}
