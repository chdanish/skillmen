package com.esc.basic.Converter;

import com.esc.basic.constant.Role;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class CSVtoListConverter implements
        AttributeConverter<Set<Role>, String> {

    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(Set<Role> roles) {
        return roles.stream().map(r -> r.name()).collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public Set<Role> convertToEntityAttribute(String s) {
        return Arrays.asList(s.split(SEPARATOR)).stream().map(Role::valueOf).collect(Collectors.toSet());

    }
}
