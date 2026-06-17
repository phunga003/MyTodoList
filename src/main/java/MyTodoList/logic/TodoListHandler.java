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
        Category targetCategory = getCategoryByName(categoryName, true);

        try {
            targetCategory.addTask(task);

        } catch (RuntimeException e) {
            throw new RuntimeException("[addTask] : " + e.getMessage());
        }
    }

    private Category getCategoryByName(String categoryName, boolean createIfNotFound) {
        for (Category c : todoList) {
            if (c.name.equals(categoryName)) {
                return c;
            }
        }

        if (createIfNotFound) {
            Category newCat = new Category(categoryName);
            todoList.add(newCat);
            return newCat;
        }

        throw new RuntimeException("Category '" + categoryName + "' not found");

    }

    // Note:  index is n-indexed, handled by callee. check Category::N_INDEX
    public Task removeTask(int index, String categoryName) {
        try {
            Category targetCategory = getCategoryByName(categoryName, false);
            return targetCategory.removeTask(index);
        } catch (RuntimeException e) {
            throw new RuntimeException("[removeTask] : " + e.getMessage());
        }
    }

    public String getDbString() {
        if (this.isEmpty()) {
            return "Todo List Empty";
        }

        StringBuilder sb = new StringBuilder();
        for (Category c : todoList) {
            sb.append(c.getCategoryString());
        }

        return sb.toString();
    }

    private boolean isEmpty() {
        if (this.todoList.size() == 1) {
            return this.defaultCategory.tasks.isEmpty();
        }

        return false;
    }

    public void moveTask(String srcCategoryName, int srcIndex, String destCategoryName) {

    }

    public void removeCategory(String categoryName) {

    }
}
