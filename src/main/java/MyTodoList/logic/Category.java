package MyTodoList.logic;

import java.util.ArrayList;

class Category {
    String name;
    ArrayList<Task> tasks;
    private static final int N_INDEX = 1;

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

    // makes UX feels better
    Task indexedRemoveTaskDecorator(int index) {
        return this.removeTask(index - N_INDEX);
    }

    String getCategoryString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        sb.append(this.name);
        sb.append(" ]\n");

        int index = N_INDEX;
        for (Task t : tasks) {
            sb.append("\t");
            sb.append(index);
            sb.append(". ");
            // callee responsible for line termination
            sb.append(t.getTaskString());
            index++;
        }

        return sb.append("\n").toString();
    }

    // for testing use
    Boolean taskNameIsInCategory(String taskName) {
        for (Task t : this.tasks) {
            if (t.name.equals(taskName)) {
                return true;
            }
        }
        return false;
    }
}
