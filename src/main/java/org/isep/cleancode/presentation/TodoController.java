package org.isep.cleancode.presentation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.isep.cleancode.Todo;
import org.isep.cleancode.application.TodoManager;
import spark.Request;
import spark.Response;

public class TodoController {

    private final Gson gson;
    private final TodoManager service;

    public TodoController(TodoManager service) {
        this.gson = new GsonBuilder()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
                .create();

        this.service = service;
    }

    public Object getAllTodos(Request req, Response res) {
        res.type("application/json");
        return gson.toJson(service.getAll());
    }

    public Object createTodo(Request req, Response res) {
        try {
            Todo newTodo = gson.fromJson(req.body(), Todo.class);
            Todo created = service.create(newTodo);
            res.status(201);
            res.type("application/json");
            return gson.toJson(created);
        } catch (JsonSyntaxException e) {
            res.status(400);
            return gson.toJson("Invalid JSON format");
        } catch (IllegalArgumentException e) {
            res.status(400);
            return gson.toJson(e.getMessage());
        } catch (Exception e) {
            res.status(500);
            return gson.toJson("Internal server error");
        }
    }
}
