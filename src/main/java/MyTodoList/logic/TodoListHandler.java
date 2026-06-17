package MyTodoList.logic;

import java.util.ArrayList;

public class TodoListHandler {
    ArrayList<Category> todoList;
    Category defaultCategory;

    public TodoListHandler() {
        todoList = new ArrayList<>();
        this.defaultCategory = new Category("default");
        todoList.add(defaultCategory);
    }

    public void addTask(Task task, String categoryName) {
        Category targetCategory = getCategoryByName(categoryName);

        try {
            targetCategory.addTask(task);

        } catch (RuntimeException e) {
            throw new RuntimeException("[addTask] : " + e.getMessage());
        }
    }

    private Category getCategoryByName(String categoryName) {
        for (Category c : todoList) {
            if (c.name.equals(categoryName)) {
                return c;
            }
        }

        Category newCat = new Category(categoryName);
        todoList.add(newCat);
        return newCat;
    }

    public Task getTask(int index, String categoryName) {
        return null;
    }

    public Task removeTask(int index, String categoryName) {
        return null;
    }

    public String getDbString() {
        return null;
    }

    public void moveTask(String srcCategoryName, int srcIndex, String destCategoryName) {

    }
}
