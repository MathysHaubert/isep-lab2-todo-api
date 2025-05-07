package org.isep.cleancode;

import java.util.Date;

public class Todo {

    // this Todo class should be completed to achieve Step 1

    private String name;

    private Date dueDate;

    public Todo(String name, Date dueDate) {

        this.name = name;
        this.dueDate = dueDate;
    }

    public Todo(String name) {
        this.name = name;
        this.dueDate = null;
    }

    public String getName() {

        return name;
    }

    public Todo setName(String name) {
        this.name = name;
        return this;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Todo setDueDate(Date dueDate) {
        this.dueDate = dueDate;
        return this;
    }
}
