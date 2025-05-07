package org.isep.cleancode;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

import org.isep.cleancode.persistence.ITodoRepository;
import org.isep.cleancode.persistence.csvfiles.TodoCsvFilesRepository;
import org.isep.cleancode.persistence.inmemory.TodoInMemoryRepository;
import org.isep.cleancode.application.TodoManager;
import org.isep.cleancode.presentation.TodoController;

import static spark.Spark.*;

public class Main {

    public static void startApp() {
        String configFilePath = Paths.get(System.getProperty("user.dir"),"config.properties").toString();

        Properties config = new Properties();
        try (FileInputStream input = new FileInputStream(configFilePath)) {
            config.load(input);
        } catch (IOException e) {
            System.err.println("Failed to load config file: " + e.getMessage());
            return;
        }

        String repoType = config.getProperty("repository", "CSV").toUpperCase();

        ITodoRepository repo = switch (repoType) {
            case "INMEMORY" -> new TodoInMemoryRepository();
            case "CSV" -> new TodoCsvFilesRepository();
            default -> throw new IllegalArgumentException("Unknown repository type in config: " + repoType);
        };

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
