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
}
