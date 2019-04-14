package com.esc.basic.service.Impl;

import com.esc.basic.service.GenericCrudService;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class GenericCrudServiceImpl<T ,I> implements GenericCrudService<T ,I> {

    private final CrudRepository<T, I> repository;

    public GenericCrudServiceImpl(CrudRepository<T, I> repository) {
        this.repository = repository;
    }

    @Override
    public CrudRepository<T, I> getRepository() {
        return this.repository;
    }

    @Override
    public Optional<T> get(I i) {
        return this.repository.findById(i);
    }

    @Override
    public Optional<List<T>> getAll() {
        List<T> list = new ArrayList<>();
        this.repository.findAll().forEach(u -> list.add(u));
        return list.size() > 0 ? Optional.ofNullable(list) : Optional.ofNullable(null);
    }

    @Override
    public Optional<T> save(T t) {
        return Optional.ofNullable(this.repository.save(t));
    }

    @Override
    public Optional<List<T>> saveAll(List<T> t) {
        List<T> list = new ArrayList<>();
        this.repository.saveAll(t).forEach(u -> list.add(u));
        return list.size() > 0 ? Optional.ofNullable(list) : Optional.ofNullable(null);
    }


    @Override
    public void delete(T t) {
        this.repository.delete(t);
    }

    @Override
    public void deleteAll(List<T> t) {
        this.repository.deleteAll(t);
    }


}
