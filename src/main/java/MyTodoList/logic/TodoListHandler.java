package MyTodoList.logic;

import MyTodoList.data.DataHandler;
import MyTodoList.data.GsonDataHandler;

import java.util.ArrayList;

public class TodoListHandler {
    ArrayList<Category> todoList;
    Category defaultCategory;
    DataHandler dataHandler;

    public TodoListHandler() {
        this.todoList = new ArrayList<>();
        this.defaultCategory = new Category("default");
        this.todoList.add(defaultCategory);
        this.dataHandler = new GsonDataHandler("MyTodoListData.json");

    }

    public TodoListHandler(String filepath) {
        this.dataHandler = new GsonDataHandler(filepath);
        this.loadDataFromFile();
        this.defaultCategory = getCategoryByName("default", true);

    }

    public void addTask(Task task, String categoryName) {
        Category targetCategory = getCategoryByName(categoryName, true);

        try {
            targetCategory.addTask(task);

        } catch (RuntimeException e) {
            throw new RuntimeException("[addTask] : " + e.getMessage());
        }
    }

    Category getCategoryByName(String categoryName, boolean createIfNotFound) {
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
        Category targetCategory;

        try {
            targetCategory = getCategoryByName(categoryName, false);

        } catch (RuntimeException e) {
            throw new RuntimeException("[removeTask] : " + e.getMessage());
        }

        try {
            return targetCategory.indexedRemoveTaskDecorator(index);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("[removeTask] : The specified task does not exists in this category");
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
        try {
            Category src = getCategoryByName(srcCategoryName, false);
            Category dest = getCategoryByName(destCategoryName, true);

            if (src.compareTo(dest) == 0) {
                throw new IllegalArgumentException("Task already in category");
            }

            dest.addTask(src.indexedRemoveTaskDecorator(srcIndex));

        } catch (RuntimeException e) {
            throw new RuntimeException("[moveTask] : " + e.getMessage());
        }
    }

    public void removeCategory(String categoryName) {
        try {
            Category targetCategory = getCategoryByName(categoryName, false);
            if (targetCategory == this.defaultCategory) {
                throw new RuntimeException("Removing default category is forbidden");
            }

            for (Task t : targetCategory.tasks) {

                try {
                    addTask(t, "default");
                } catch (RuntimeException e) {
                    // skip task
                }
            }

            this.todoList.remove(targetCategory);

        } catch (RuntimeException e) {
            throw new RuntimeException("[removeCategory] : " + e.getMessage());
        }

    }

    void loadDataFromFile() {
        this.todoList = dataHandler.deserialize();
    }

    public void writeDataToFile() {
        dataHandler.serialize(this.todoList);
    }
}
