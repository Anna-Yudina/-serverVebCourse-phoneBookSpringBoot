package ru.yudina.springcourse.dao;

import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {
    @Transactional
    void create(T obj);

    @Transactional
    void update(T obj);

    @Transactional
    void remove(int id);

    T getById(PK id);

    @Transactional
    List<T> findAll();
}
