package MyTodoList.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    void CategoryTest01_simple_construct() {
        Category category = new Category("Fred");

        Assertions.assertEquals(0, category.tasks.size());
        Assertions.assertEquals("Fred", category.name);
    }

    @Test
    void CategoryTest02_simple_add_task() {
        Category category = new Category("Fred");
        category.addTask(new Task("Fazz"));
        Assertions.assertEquals("Fazz", category.tasks.get(0).name);
    }

    @Test
    void CategoryTest03_disallow_duplicate_tasks() {
        boolean failed = false;
        Category category = new Category("Fred");
        category.addTask(new Task("Fazz"));

        try {
            category.addTask(new Task("Fazz"));
        } catch (RuntimeException e) {
            Assertions.assertEquals("Task with the same name already existed", e.getMessage());
            failed = true;
        }

        Assertions.assertTrue(failed);
    }

    @Test
    void CategoryTest04_adding_many_task() {
        Category category = new Category("Fred");
        category.addTask(new Task("ar"));
        category.addTask(new Task("arr"));
        category.addTask(new Task("arrr"));
        category.addTask(new Task("arrrr"));

        Assertions.assertEquals(4, category.tasks.size());

        for (Task t : category.tasks) {
            try {
                category.addTask(new Task(t.name));
            } catch (RuntimeException e) {
                Assertions.assertEquals("Task with the same name already existed", e.getMessage());
            }
        }

        // Ensure no new entries are added illegally
        Assertions.assertEquals(4, category.tasks.size());
    }

    @Test
    void CategoryTest05_remove_task_simple() {
        Category category = new Category("Cooper");
        category.addTask(new Task("t1"));

        Task removedTask = category.removeTask(0);
        Assertions.assertEquals(0, category.tasks.size());
        Assertions.assertEquals("t1", removedTask.name);
    }

    @Test
    void CategoryTest05_remove_task_arbitrary_idx_out_of_range() {
        Category category = new Category("Cooper");
        category.addTask(new Task("t1"));

        boolean failed = false;
        try {
            category.removeTask(1);
        } catch (RuntimeException e) {
            Assertions.assertEquals("No task with the index 1 exists in this category", e.getMessage());
            failed = true;
        }

        Assertions.assertTrue(failed);
    }

    @Test
    void CategoryTest06_remove_task_arbitrary_idx_out_of_range_negative() {
        Category category = new Category("Cooper");
        category.addTask(new Task("t1"));

        boolean failed = false;
        try {
            category.removeTask(-1);
        } catch (RuntimeException e) {
            Assertions.assertEquals("No task with the index -1 exists in this category", e.getMessage());
            failed = true;
        }

        Assertions.assertTrue(failed);
    }

    @Test
        // NOTE: This test assumes tasks is sorted by order of insert,
        // and the tasks are being kept as an arraylist and performs a
        // call-through to ArrayList.remove()
        // Suggestion for extending Category: inherit Category and implement a sort() function
    void CategoryTest07_remove_task_arbitrary_idx() {
        Category category = new Category("Sam");
        category.addTask(new Task("t1"));
        category.addTask(new Task("t3"));
        category.addTask(new Task("t2"));
        category.addTask(new Task("t4"));

        Task taskRemoved = category.tasks.remove(2);
        Assertions.assertEquals("t2", taskRemoved.name);

        taskRemoved = category.tasks.remove(2);
        Assertions.assertEquals("t4", taskRemoved.name);

        taskRemoved = category.tasks.remove(0);
        Assertions.assertEquals("t1", taskRemoved.name);

        Task remainingTask = category.tasks.get(0);
        Assertions.assertEquals("t3", remainingTask.name);

    }

    @Test
    void CategoryTest08_get_category_string() {
        Category category = new Category("Larry");
        category.addTask(new Task("t1"));

        Assertions.assertEquals("[ Larry ]\n\t1. t1\n\n", category.getCategoryString());
    }

}
