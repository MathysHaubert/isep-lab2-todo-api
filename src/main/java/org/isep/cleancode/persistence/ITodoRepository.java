package org.isep.cleancode.persistence;


import org.isep.cleancode.Todo;

import java.util.List;

public interface ITodoRepository {
    void save(Todo todo);
    List<Todo> findAll();
    boolean existsByName(String name);
}

