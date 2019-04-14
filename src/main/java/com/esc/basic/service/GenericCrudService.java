package com.esc.basic.service;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GenericCrudService< T , I > {

    CrudRepository<T, I> getRepository();

    Optional<T> get(I i);

    Optional<List<T>> getAll();

    Optional<T> save(T t);

    Optional<List<T>> saveAll(List<T> t);

    Optional<T> update(T t);

    void delete(T t);

    void deleteAll(List<T> t);

}
