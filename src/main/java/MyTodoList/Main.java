package MyTodoList;

import MyTodoList.logic.Task;
import MyTodoList.logic.TodoListHandler;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TodoListHandler handler;

        try {
            handler = new TodoListHandler(args[0]);
        } catch (RuntimeException e) {
            System.out.println("Unable to load file: " + args[0] + "\nStarting from empty database");
            System.out.println(e.getMessage());
            handler = new TodoListHandler();
        }

        Path p = Paths.get(args[0]).toAbsolutePath();
        System.out.println("Reading from: " + p);

        System.out.println("-- MY TODO LIST --");
        printAppUsage();

        while (true) {
            System.out.println(handler.getDbString());
            System.out.print("$> ");
            String userInput = scanner.nextLine();
            System.out.println();

            if (!userInput.isEmpty()) {
                switch (userInput.charAt(0)) {
                    case 'q':
                        System.out.println("Goodbye!");
                        return;
                    case '+':
                        System.out.print("Task name: ");
                        String name = scanner.nextLine();
                        String category = "";
                        System.out.print("Category name (optional): ");
                        category = scanner.nextLine();
                        try {
                            if (category.isEmpty()) {
                                handler.addTask(new Task(name), "default");
                            } else {
                                handler.addTask(new Task(name), category);
                            }
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }

                        break;
                    case '-':
                        System.out.print("Task Index: ");
                        String index = scanner.nextLine();
                        System.out.print("Category name (required): ");
                        String categoryName = scanner.nextLine();
                        try {
                            int indexNum = Integer.parseInt(index);
                            handler.removeTask(indexNum, categoryName);
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 'm':
                        System.out.print("Source Category: ");
                        String src = scanner.nextLine();
                        System.out.print("Task Index: ");
                        String idx = scanner.nextLine();
                        System.out.print("Destination Category: ");
                        String dest = scanner.nextLine();

                        try {
                            int indexNum = Integer.parseInt(idx);
                            handler.moveTask(src, indexNum, dest);
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 'r':
                        System.out.print("Target Category: ");
                        String response = scanner.nextLine();
                        try {
                            handler.removeCategory(response);
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 'w':
                        try {
                            System.out.println("Serizalizing data...");
                            handler.writeDataToFile();
                            System.out.println("Done");

                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }


                    default:
                        printAppUsage();
                }
            }
        }
    }


    private static void printAppUsage() {
        System.out.println(
                """
                        q\tquit application
                        +\tadd task to a category. Tasks with no category will be in "default"
                        -\tremove a task
                        m\tmove task with index from src to dest category
                        r\tremove a category. Its contents will be transfered to the 'default' category
                        w\twrite current data to file
                        """);
    }
}