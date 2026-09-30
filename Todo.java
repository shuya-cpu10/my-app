import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Todo {

    public void start(Scanner scanner, AppData appData) {

        ArrayList<TodoItem> todos = Database.loadTodos();
        boolean running = true;

        while (running)
            {
            LocalDateTime now = LocalDateTime.now();

            for (int i = todos.size() - 1; i >= 0; i--) {

            TodoItem todo = todos.get(i);

            if (!todo.completed && todo.dueDateTime.isBefore(now)) {
            Database.deleteTodo(todo.id);
            appData.score -= 20;
            todos.remove(i);

    }
}
            System.out.println("=== Todo App ===");

            System.out.println("amount of score:"+appData.score);

            System.out.println("1. Add Todo");
            System.out.println("2. Show Todos");
            System.out.println("3.Complete Todo");
            System.out.println("4.show Completed Todos");
            System.out.println("5. Exit");
            System.out.print("Choose a number: ");

            int number = scanner.nextInt();
            scanner.nextLine();

            switch (number) {

                case 1:
                    System.out.print("Enter a Todo: ");

                    String newTodo = scanner.nextLine();
                    System.out.println(
                        "Enter due date and time (YYYY-MM-DD HH:MM):"
                    );
                    String dateTimeInput = scanner.nextLine();
                    DateTimeFormatter formatter=
                     DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime dueDateTime =
                     LocalDateTime.parse(dateTimeInput, formatter);
                    TodoItem newTodoItem = new TodoItem();
                    newTodoItem.title=newTodo;
                    newTodoItem.completed=false;
                    newTodoItem.dueDateTime = dueDateTime;
                    todos.add(newTodoItem);
                     Database.saveTodo(newTodoItem);
                
                    System.out.println("Todo added!");
                    break;

                case 2:
                    System.out.println();
                    System.out.println("=== Todo List ===");

                    if (todos.size() == 0) {
                        System.out.println("No Todos yet.");
                    } else {
                        for (int i = 0; i < todos.size(); i++) {
                            TodoItem todo = todos.get(i);
                            if (todo.completed){
                                System.out.println(
                                    (i+1) + ".[done]" + todo.title                   
                                );
                                System.out.println("Due:"+todo.dueDateTime
                                );
                            } else {
                                System.out.println(
                                    (i+1) + ".[]" + todo.title
                                );
                                System.out.println("Due: " + todo.dueDateTime);
                            }
                        }
                    }


                    break;

                case 3:
    System.out.println();
    System.out.println("=== Choose a Todo ===");

    int displayNumber = 1;

    for (int i = 0; i < todos.size(); i++) {

        TodoItem todo = todos.get(i);

        if (!todo.completed) {

            System.out.println(
                displayNumber + ". [] " + todo.title
            );

            displayNumber++;
        }
    }

    if (displayNumber == 1) {
        System.out.println("No incomplete Todos.");
        break;
    }

    System.out.println("Which Todo did you complete?");

    int todonumber = scanner.nextInt();
    scanner.nextLine();

    int incompleteNumber = 1;
    TodoItem selectedTodo = null;

    for (int i = 0; i < todos.size(); i++) {

        TodoItem todo = todos.get(i);

        if (!todo.completed) {

            if (incompleteNumber == todonumber) {
                selectedTodo = todo;
                break;
            }

            incompleteNumber++;
        }
    }

    if (selectedTodo == null) {
        System.out.println("Invalid Todo number.");
        break;
    }

    selectedTodo.completed = true;

    Database.completeTodo(selectedTodo.id);

    appData.score += 10;

    System.out.println(
        todonumber + ".[done] " + selectedTodo.title
    );

    break;
                case 4:
                    System.out.println("===completed Todos===");
                    boolean hasCompletedTodo = false;
                    for(int i = 0; i < todos.size(); i++){
                        TodoItem todo = todos.get(i);
                          if (todo.completed){
                            hasCompletedTodo = true;
                            System.out.println((i+1)+"[done]"+todo.title);
                          }
                    }
                    if (!hasCompletedTodo) {
                        System.out.println("No completed Todos.");
                    }   
                    break;
                  
                    
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println(
                        "Please enter a number from 1 to 5."
                    );
            }
        }
    }
}