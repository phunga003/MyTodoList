package MyTodoList.logic;

public class Task implements Comparable<Task> {
    String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Task t) {
        return 0;
    }
}
