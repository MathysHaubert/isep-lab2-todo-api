package org.isep.cleancode.persistence.csvfiles;

import org.isep.cleancode.Todo;
import org.isep.cleancode.persistence.ITodoRepository;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class TodoCsvFilesRepository implements ITodoRepository {

    private final Path filePath;
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public TodoCsvFilesRepository() {
        this.filePath = resolveFilePath();
        createFileIfNeeded();
    }

    private Path resolveFilePath() {
        // Je ne vois pas l'utilité de d'aller dans le APPDATA (pour windows) ou dans le HOME (pour linux) pour stocker des données ?
        return Paths.get(System.getProperty("user.dir"),"data","app.csv");
    }


    private void createFileIfNeeded() {
        try {
            Files.createDirectories(filePath.getParent());
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize CSV file", e);
        }
    }

    public void save(Todo todo) {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND)) {
            String dueDateStr = (todo.getDueDate() == null) ? "" : formatter.format(todo.getDueDate());
            writer.write(todo.getName() + ";" + dueDateStr);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write todo to CSV", e);
        }
    }

    public List<Todo> findAll() {
        List<Todo> todos = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", -1);
                String name = parts[0];
                Date dueDate = null;
                if (parts.length > 1 && !parts[1].isEmpty()) {
                    dueDate = formatter.parse(parts[1]);
                }
                todos.add(new Todo(name, dueDate));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read todos from CSV", e);
        }
        return todos;
    }

    public boolean existsByName(String name) {
        return findAll().stream().anyMatch(t -> t.getName().equals(name));
    }
}
