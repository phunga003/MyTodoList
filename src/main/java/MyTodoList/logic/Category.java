package MyTodoList.logic;

import java.util.ArrayList;

class Category {
    String name;
    ArrayList<Task> tasks;

    public Category(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }

    void addTask(Task task) {
        if (isNotDuplicateTask(task))
            this.tasks.add(task);
    }

    private boolean isNotDuplicateTask(Task task) {
        for (Task t : this.tasks) {

        }
        return true;
    }

}
