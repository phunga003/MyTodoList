package MyTodoList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyDataTest {

    @Test
    public void test() {
        MyData data = new MyData();

        assertEquals(5, data.num, "sample test");
        assertEquals(false, data.fizz);

    }
}
