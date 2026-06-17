package MyTodoList.logic;

import MyTodoList.data.GsonDataHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataHandlerTest {

    @Test
    void test01_dataWriteHappy() {
        Category c1 = new Category("c1");
        Category c2 = new Category("c2");

        c1.tasks.add(new Task("hello"));
        c1.tasks.add(new Task("world"));

        c2.tasks.add(new Task("ciao"));
        c2.tasks.add(new Task("kasdj"));
        c2.tasks.add(new Task("fizzy"));

        ArrayList<Category> arr = new ArrayList<>(List.of(new Category[]{c1, c2}));
        GsonDataHandler dataHandler = new GsonDataHandler("data.json");
        String expectedOutput =
                """
                        [
                          {
                            "name": "c1",
                            "tasks": [
                              {
                                "name": "hello"
                              },
                              {
                                "name": "world"
                              }
                            ]
                          },
                          {
                            "name": "c2",
                            "tasks": [
                              {
                                "name": "ciao"
                              },
                              {
                                "name": "kasdj"
                              },
                              {
                                "name": "fizzy"
                              }
                            ]
                          }
                        ]""";
        Assertions.assertEquals(expectedOutput, dataHandler.getJsonString(arr));
    }

    @Test
    void test02_dataReadHappy() {
        GsonDataHandler dataHandler = new GsonDataHandler("src/test/resources/seedData.json");
        ArrayList<Category> data = dataHandler.deserialize();

        Assertions.assertEquals(2, data.size());

    }

    @Test
    void test02_dataRead_integration() {
        TodoListHandler handler = new TodoListHandler("src/test/resources/seedData.json");
        handler.loadDataFromFile();

        Assertions.assertEquals(
                """
                        [ default ]
                        \t1. hello
                        \t2. world
                        \t3. how are you
                        
                        [ c2 ]
                        \t1. ciao
                        \t2. Joe
                        \t3. fizzy
                        
                        """, handler.getDbString());

    }

    @Test
    void test03_data_read_Write_integration() {
        TodoListHandler handler = new TodoListHandler("src/test/resources/seedDataTemp.json");
        handler.addTask(new Task("go outside"), "done by today");
        handler.addTask(new Task("baseball"), "done by today");
        handler.addTask(new Task("clean the room"), "within this week");
        handler.moveTask("within this week", 0, "done by today");
        handler.writeDataToFile();
        handler.loadDataFromFile();
        handler.moveTask("done by today", 1, "within this week");
        handler.removeCategory("within this week");

        Assertions.assertEquals(
                """
                        [ default ]
                        \t1. baseball
                        
                        [ done by today ]
                        \t1. go outside
                        \t2. clean the room
                        
                        """, handler.getDbString());

        try {
            Files.deleteIfExists(Path.of("src/test/resources/seedDataTemp.json"));
        } catch (IOException e) {
            // Don't care, it is a temp file
        }
    }
}
