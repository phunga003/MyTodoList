package MyTodoList.logic;

public class Category implements Comparable<Category> {
    private String name;


    @Override
    public int compareTo(Category c) {
        return this.name.compareTo(c.name);
    }
}
