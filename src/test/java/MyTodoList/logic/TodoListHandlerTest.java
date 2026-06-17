package MyTodoList.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TodoListHandlerTest {

    @Test
    void TodoListHandlerTest01_simple_construction() {
        TodoListHandler handler = new TodoListHandler();
        Assertions.assertEquals(1, handler.todoList.size());
        Assertions.assertTrue(handler.defaultCategory.tasks.isEmpty());
        Assertions.assertEquals("default", handler.defaultCategory.name);
    }

    @Test
    void TodoListHandlerTest02_simple_add_task_simple() {
        TodoListHandler handler = new TodoListHandler();
        handler.addTask(new Task("t1"), "default");

        Assertions.assertTrue(
                handler.defaultCategory.taskNameIsInCategory("t1"));
    }

    @Test
    void TodoListHandlerTest03_simple_add_task_non_existent_category() {
        TodoListHandler handler = new TodoListHandler();
        handler.addTask(new Task("fizzbuzz"), "foobar");

        Assertions.assertEquals(2, handler.todoList.size());

        // Note: Assume Category is sorted by order of insert and
        // underlying datastructures is ArrayList
        Category targetCat = handler.todoList.get(1);
        Task targetTask = targetCat.tasks.get(0);
        Assertions.assertEquals("fizzbuzz", targetTask.name);
    }

    @Test
    void TodoListHandlerTest04_remove_Task_simple() {
        TodoListHandler handler = new TodoListHandler();
        Category category = new Category("foobar");
        category.tasks.add(new Task("fizzbuzz"));
        category.tasks.add(new Task("fazzbear"));
        handler.todoList.add(category);

        Task targetTask = handler.removeTask(0, "foobar");
        Assertions.assertEquals("fizzbuzz", targetTask.name);

        Assertions.assertFalse(category.taskNameIsInCategory("fizzbuzz"));
        Assertions.assertEquals(1, category.tasks.size());
    }

    @Test
    void TodoListHandlerTest04_remove_Task_not_exist() {
        TodoListHandler handler = new TodoListHandler();
        Category category = new Category("foobar");
        handler.todoList.add(category);

        boolean failed = false;
        try {
            Task targetTask = handler.removeTask(0, "foobar");
            Assertions.assertEquals("fizzbuzz", targetTask.name);
        } catch (RuntimeException e) {
            failed = true;
            Assertions.assertEquals("[removeTask] : No task exists in this category", e.getMessage());

        }

        Assertions.assertTrue(failed);

    }

    @Test
    void TodoListHandlerTest05_remove_Task_not_exist() {
        TodoListHandler handler = new TodoListHandler();
        Category category = new Category("foobar");
        category.tasks.add(new Task("fazzbear"));
        handler.todoList.add(category);

        boolean failed = false;
        try {
            Task targetTask = handler.removeTask(1, "foobar");
            Assertions.assertEquals("fizzbuzz", targetTask.name);
        } catch (RuntimeException e) {
            failed = true;
            Assertions.assertEquals("[removeTask] : No task with the index 1 exists in this category", e.getMessage());

        }

        Assertions.assertTrue(failed);

    }

    @Test
    void TodoListHandlerTest06_remove_Task_not_exist() {
        TodoListHandler handler = new TodoListHandler();
        Category category = new Category("foobar");
        category.tasks.add(new Task("fazzbear"));
        handler.todoList.add(category);

        boolean failed = false;
        try {
            Task targetTask = handler.removeTask(-1, "foobar");
            Assertions.assertEquals("fizzbuzz", targetTask.name);
        } catch (RuntimeException e) {
            failed = true;
            Assertions.assertEquals("[removeTask] : No task with the index -1 exists in this category", e.getMessage());

        }

        Assertions.assertTrue(failed);

    }

}
