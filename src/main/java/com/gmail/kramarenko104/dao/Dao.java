package com.gmail.kramarenko104.dao;

import java.util.List;

public interface Dao<T> {

    long save(T t);

    T get(long id);

    long delete(long id);

    long update(T t);

    List<T> getAll();
}