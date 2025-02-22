package com.example.projet.dao;

import java.util.List;

public interface GenericDao<T, T1> {
    List<T> findAll();
    T findById(T1 id);
    void save(T entity);
    void update(T entity);
    void delete(T1 id);
}
