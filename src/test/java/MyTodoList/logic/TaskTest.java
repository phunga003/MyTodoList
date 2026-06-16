package MyTodoList.logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TaskTest {
    @Test
    void TaskTest01_simple_construct() {
        String expectedName = "FizzBuzz123";
        String expectedDesc = "foobar";
        Task task = new Task("FizzBuzz123", "foobar");

        Assertions.assertEquals(expectedName, task.name);
        Assertions.assertEquals(expectedDesc, task.description);


    }
}
