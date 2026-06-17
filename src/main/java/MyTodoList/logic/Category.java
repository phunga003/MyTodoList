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
        if (this.tasks.isEmpty()) {
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

    Task removeTask(int index) {
        if (this.tasks.isEmpty()) {
            throw new IllegalArgumentException("No task exists in this category");
        }

        if (index < 0 || this.tasks.size() - 1 < index) {
            throw new IllegalArgumentException("No task with the index " + index + " exists in this category");
        }

        return this.tasks.remove(index);
    }

}
