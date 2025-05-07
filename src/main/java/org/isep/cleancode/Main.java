package org.isep.cleancode;

import static spark.Spark.*;

import org.isep.cleancode.persistence.TodoRepository;
import org.isep.cleancode.presentation.TodoController;
import org.isep.cleancode.service.TodoManager;

public class Main {

    public static void startApp() {

        TodoRepository repo = new TodoRepository();
        TodoManager manager = new TodoManager(repo);
        TodoController controller = new TodoController(manager);

        port(4567);
        get("/todos", controller::getAllTodos);
        post("/todos", controller::createTodo);
    }

    public static void stopApp() {
        stop();
    }

    public static void main(String[] args) {
        startApp();
    }
}


