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

        Task targetTask = handler.removeTask(1, "foobar");
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
            Task targetTask = handler.removeTask(2, "foobar");
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
            Assertions.assertEquals("[removeTask] : No task with the index -2 exists in this category", e.getMessage());

        }

        Assertions.assertTrue(failed);

    }

    @Test
    void TodoListHandlerTest07_remove_Task_category_not_exist() {
        TodoListHandler handler = new TodoListHandler();

        boolean failed = false;
        try {
            Task targetTask = handler.removeTask(-1, "foobar");
            Assertions.assertEquals("fizzbuzz", targetTask.name);
        } catch (RuntimeException e) {
            failed = true;
            Assertions.assertEquals(
                    "[removeTask] : Category 'foobar' not found",
                    e.getMessage());

        }

        Assertions.assertTrue(failed);

    }

    @Test
    void TodoListHandlerTest08_getDbString_empty() {
        TodoListHandler handler = new TodoListHandler();
        Assertions.assertEquals(
                "Todo List Empty",
                handler.getDbString());
    }

    @Test
    void TodoListHandlerTest09_getDbString_default_one_category() {
        TodoListHandler handler = new TodoListHandler();
        handler.defaultCategory.tasks.add(new Task("Drink Matcha Labubu"));
        Assertions.assertEquals(
                "[ default ]\n\t1. Drink Matcha Labubu\n\n",
                handler.getDbString());
    }

    @Test
    void TodoListHandlerTest10_getDbString_default_many_category() {
        TodoListHandler handler = new TodoListHandler();
        handler.defaultCategory.tasks.add(new Task("Drink Matcha Labubu"));

        Category category = new Category("foobar");
        category.tasks.add(new Task("fazzbear"));
        category.tasks.add(new Task("fizzfazz"));
        handler.todoList.add(category);

        Assertions.assertEquals(
                "[ default ]\n\t1. Drink Matcha Labubu\n\n[ foobar ]\n\t1. fazzbear\n\t2. fizzfazz\n\n",
                handler.getDbString());
    }

    @Test
    void TodoListHandlerTest11_moveTask_both_category_exist() {
        TodoListHandler handler = new TodoListHandler();
        handler.defaultCategory.tasks.add(new Task("Drink Matcha Labubu"));

        Category category = new Category("foobar");
        category.tasks.add(new Task("fazzbear"));
        category.tasks.add(new Task("fizzfazz"));
        handler.todoList.add(category);

        handler.moveTask("foobar", 1, "default");
        Task movedTask = handler.defaultCategory.tasks.get(1);
        Assertions.assertEquals("fazzbear", movedTask.name);

        Assertions.assertEquals(1, category.tasks.size());
    }

    @Test
    void TodoListHandlerTest12_moveTask_dest_not_exist() {
        TodoListHandler handler = new TodoListHandler();
        handler.defaultCategory.tasks.add(new Task("Drink Matcha Labubu"));

        Category category = new Category("foobar");
        category.tasks.add(new Task("fazzbear"));
        category.tasks.add(new Task("fizzfazz"));
        handler.todoList.add(category);

        handler.moveTask("foobar", 1, "hello");
        Category newCategory = handler.getCategoryByName("hello", false);
        Assertions.assertEquals("fazzbear", newCategory.tasks.get(0).name);
    }

    @Test
    void TodoListHandlerTest13_moveTask_src_not_exist() {
        TodoListHandler handler = new TodoListHandler();
        handler.defaultCategory.tasks.add(new Task("Drink Matcha Labubu"));


        boolean failed = false;
        try {
            handler.moveTask("foobar", 0, "default");
        } catch (RuntimeException e) {
            failed = true;
            Assertions.assertEquals(
                    "[moveTask] : Category 'foobar' not found",
                    e.getMessage());

        }

        Assertions.assertTrue(failed);

    }

    @Test
    void TodoListHandlerTest14_moveTask_same_category() {
        TodoListHandler handler = new TodoListHandler();
        handler.defaultCategory.tasks.add(new Task("Drink Matcha Labubu"));


        boolean failed = false;
        try {
            handler.moveTask("default", 0, "default");
        } catch (RuntimeException e) {
            failed = true;
            Assertions.assertEquals(
                    "[moveTask] : Task already in category",
                    e.getMessage());

        }

        Assertions.assertTrue(failed);

    }

    @Test
    void TodoListHandlerTest15_moveTask_index_invalid() {
        TodoListHandler handler = new TodoListHandler();
        handler.defaultCategory.tasks.add(new Task("Drink Matcha Labubu"));


        boolean failed = false;
        try {
            handler.moveTask("default", -1, "newCat");
        } catch (RuntimeException e) {
            failed = true;
            Assertions.assertEquals(
                    "[moveTask] : No task with the index -2 exists in this category",
                    e.getMessage());

        }

        Assertions.assertTrue(failed);

    }

    @Test
    void TodoListHandlerTest16_removeCategory_category_not_exist() {
        TodoListHandler handler = new TodoListHandler();

        boolean failed = false;
        try {
            handler.removeCategory("foobazz");
        } catch (RuntimeException e) {
            failed = true;
            Assertions.assertEquals(
                    "[removeCategory] : Category 'foobazz' not found",
                    e.getMessage());

        }

        Assertions.assertTrue(failed);
    }

    @Test
    void TodoListHandlerTest17_removeCategory_removing_default() {
        TodoListHandler handler = new TodoListHandler();

        boolean failed = false;
        try {
            handler.removeCategory("default");
        } catch (RuntimeException e) {
            failed = true;
            Assertions.assertEquals(
                    "[removeCategory] : Removing default category is forbidden",
                    e.getMessage());

        }

        Assertions.assertTrue(failed);
    }

    @Test
    void TodoListHandlerTest18_removeCategory_removing_nonempty_category() {
        TodoListHandler handler = new TodoListHandler();

        Category category = new Category("foobar");
        category.tasks.add(new Task("fazzbear"));
        category.tasks.add(new Task("fizzfazz"));
        handler.todoList.add(category);

        handler.removeCategory("foobar");
        Assertions.assertEquals(1, handler.todoList.size());

        for (Task t : category.tasks) {
            Assertions.assertTrue(handler.defaultCategory.taskNameIsInCategory(t.name));
        }
    }

    @Test
    void TodoListHandlerTest19_removeCategory_removing_empty_category() {
        TodoListHandler handler = new TodoListHandler();

        Category category = new Category("foobar");
        handler.todoList.add(category);

        handler.removeCategory("foobar");
        Assertions.assertEquals(1, handler.todoList.size());
        Assertions.assertEquals(0, handler.defaultCategory.tasks.size());


    }


}
