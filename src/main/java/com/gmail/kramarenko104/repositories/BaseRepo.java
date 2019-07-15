package com.gmail.kramarenko104.repositories;

import java.util.List;

public interface BaseRepo<T> {

    T get(long id);

    T update(T newT);

    void delete(long id);

    List<T> getAll();
}
