package org.isep.cleancode.application;

import org.isep.cleancode.Todo;
import org.isep.cleancode.persistence.ITodoRepository;

import java.util.List;

public class TodoManager {

    private final ITodoRepository repository;

    public TodoManager(ITodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> getAll() {
        return repository.findAll();
    }

    public Todo create(Todo todo) {
        validate(todo);
        repository.save(todo);
        return todo;
    }

    private void validate(Todo newTodo) {
        if (newTodo.getName() == null || newTodo.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Todo name cannot be null or empty");
        }

        for (Todo t : this.repository.findAll()) {
            if (t.getName().equals(newTodo.getName())) {
                throw new IllegalArgumentException("Todo name must be unique");
            }
        }
    }
}
