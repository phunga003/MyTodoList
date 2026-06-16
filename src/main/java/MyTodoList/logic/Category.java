package MyTodoList.logic;

import java.util.ArrayList;

class Category {
    String name;
    ArrayList<Task> tasks;

    public Category(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }

}
