package MyTodoList.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TaskTest {
    @Test
    void TaskTest01_simple_construct() {
        String expectedName = "FizzBuzz123";
        Task task = new Task("FizzBuzz123");

        Assertions.assertEquals(expectedName, task.name);
    }

}
