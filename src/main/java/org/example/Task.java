package org.example;

import java.time.LocalDate;

public class Task {
    private String name;
    private String priority;
    private LocalDate dueDate;

    public Task(String description, String priority, LocalDate dueDate) {
        this.name = description;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public String getPriority(){
        return priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return "org.example.Task: " + name + "Priority: " + priority + "Due: " + dueDate;
    }
}
