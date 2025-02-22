package com.example.projet.dao;

import java.util.List;

public interface CategoryDao<T,ID> {
    List<T> findAll();
    T findById(ID id);
    void save(T entity);
    void update(T entity);
    void delete(ID id);
}
