package org.example;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static final String SAVE_FILE = "saved_tasks.txt";

    public static void main(String[] args) {
        String[] allowedCommands = {"add", "delete", "list", "info", "save", "view"};
        boolean running = true;

        Main.info(allowedCommands);
        loadTasks();

        while (running) {
            System.out.println("Waiting for your next command:");
            String command = scanner.nextLine();
            switch (command) {
                case "add":
                    addTask();
                    break;
                case "delete":
                    deleteTask();
                    break;
                case "list":
                    listTasks();
                    break;
                case "info":
                    Main.info(allowedCommands);
                    break;
                case "save":
                    saveTasks();
                    running = false;
                    break;
                case "view":
                    viewTask();
                    break;
                default:
                    System.out.println("Unknown command, for command list type info");
            }
        }
    }

    public static void info(String[] allowedCommands) {
        System.out.println("Allowed system commands:");
        for (String item : allowedCommands) {
            System.out.println("# " + item);
        }
    }

    public static void addTask() {
        String description;
        String priority;
        LocalDate dueDate;
        while (true) {
            System.out.println("Enter new task name: ");
            description = scanner.nextLine();

            if (!description.isEmpty()) {
                break;
            } else {
                System.out.println("Task name cannot be empty. Enter valid name");
            }
        }
        while (true) {
            System.out.println("Enter task priority (high, medium, low): ");
            priority = scanner.nextLine().trim().toLowerCase();

            if (priority.equals("high") || priority.equals("medium") || priority.equals("low")) {
                break;
            } else {
                System.out.println("Invalid priority. Please enter a valid option");
            }
        }

        while (true) {
            System.out.println("Enter the due date (YYYY-MM-DD): ");
            try {
                dueDate = LocalDate.parse(scanner.nextLine().trim(), DateTimeFormatter.ISO_LOCAL_DATE);
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter valid format");
            }
        }

        Task task = new Task(description, priority, dueDate);
        tasks.add(task);
        System.out.println("Task has been added");
    }

    public static void deleteTask() {
        boolean deleted = false;
        System.out.println("Write name of a task to delete:");
        String input = scanner.nextLine();

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getName().equalsIgnoreCase(input)) {
                tasks.remove(i);
                System.out.println("Task has been deleted");
                deleted = true;
                break;
            }
        }

        if (!deleted) {
            System.out.println("Task with name '" + input + "' not found. Use list to view all your tasks");
        }
    }

    private static void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Your list is empty. Add new task with 'add' command");
        } else {
            int num = 1;
            for (Task task : tasks) {
                System.out.println(num + ". " + task.getName());
                num++;
            }
        }
    }

    private static void viewTask() {
        boolean exists = false;
        System.out.println("Type name of the task");
        String input = scanner.nextLine();
        for (Task task : tasks) {
            if (task.getName().equalsIgnoreCase(input)) {
                System.out.println("Task info:");
                System.out.println("Task name: " + task.getName() + " Priority: " + task.getPriority() + " Due: " + task.getDueDate());
                exists = true;
                break;
            }
        }

        if (!exists) {
            System.out.println("Task with name '" + input + "' not found. Use list to view all your tasks");
        }
    }

    private static void saveTasks(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))){
            for (Task task: tasks){
                writer.write(task.getName() + "|" + task.getPriority() + "|" + task.getDueDate());
                writer.newLine();
            }
            System.out.println("List has been save to " + SAVE_FILE);
        } catch (IOException e){
            System.out.println("Error has occurred" + e.getMessage());
        }
    }

    private static void loadTasks(){
        File file = new File(SAVE_FILE);
        if (!file.exists()){
            System.out.println("No save file found. Starting with empty list");
            System.out.println("If you think this is a mistake make sure there is a file in correct directory");
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(SAVE_FILE))){
            String line;
            while((line = bufferedReader.readLine()) != null){
                String[] parts = line.split("\\|");
                if (parts.length == 3){
                    String description = parts[0];
                    String priority = parts[1];
                    LocalDate dueDate = LocalDate.parse(parts[2], DateTimeFormatter.ISO_LOCAL_DATE);
                    tasks.add(new Task(description, priority, dueDate));
                }
            }
            System.out.println("Task have been loaded");
        } catch (IOException e){
            System.out.println("Error has occurred while loading tasks: " + e.getMessage());
        }
    }
}