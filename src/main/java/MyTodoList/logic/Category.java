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
        if (tasks.isEmpty()) {
            this.tasks.add(task);
        } else if (isNotDuplicateTask(task))
            this.tasks.add(task);
        else {
            throw new IllegalArgumentException("Task with the same name already existed");
        }
    }

    private boolean isNotDuplicateTask(Task task) {
        for (Task t : this.tasks) {
            if (t.compareTo(task) == 0) {
                return false;
            }
        }
        return true;
    }

}
