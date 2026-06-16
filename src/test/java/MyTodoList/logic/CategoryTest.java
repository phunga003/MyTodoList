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


}
