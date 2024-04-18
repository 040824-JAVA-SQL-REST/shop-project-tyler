package com.revature.shop.daos;

import java.util.List;

public interface CrudDao<T> {
    T save(T obj);

    T update(T obj);

    boolean delete(String id);

    List<T> findAll();

    T findById(String id);
}
