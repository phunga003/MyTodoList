package MyTodoList.data;

import MyTodoList.logic.Category;

import java.util.ArrayList;

public interface DataHandler {

    void serialize(ArrayList<Category> db);

    ArrayList<Category> deserialize();
}
