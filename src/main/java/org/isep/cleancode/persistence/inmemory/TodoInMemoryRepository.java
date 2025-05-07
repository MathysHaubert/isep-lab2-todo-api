package org.isep.cleancode.persistence.inmemory;

import org.isep.cleancode.Todo;
import org.isep.cleancode.persistence.ITodoRepository;

import java.util.ArrayList;
import java.util.List;

public class TodoInMemoryRepository implements ITodoRepository {
    private final List<Todo> todos = new ArrayList<>();

    public List<Todo> findAll() {
        return todos;
    }

    public void save(Todo todo) {
        todos.add(todo);
    }

    public boolean existsByName(String name) {
        return todos.stream().anyMatch(t -> t.getName().equals(name));
    }
}
