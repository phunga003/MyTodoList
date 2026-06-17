package MyTodoList.data;

import MyTodoList.logic.Category;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class GsonDataHandler implements DataHandler {
    private final Gson gson;
    Path filePath;

    public GsonDataHandler(String filePath) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.filePath = Path.of(filePath);
    }


    private void writeToFile(String s) {
        try {
            Files.writeString(filePath, s);
        } catch (Exception e) {
            throw new RuntimeException("Unable to serialize data: " + e.getMessage());
        }

    }

    private String readFromFile() {
        try {
            return Files.readString(filePath);
        } catch (Exception e) {
            throw new RuntimeException("Unable to deserialize data: " + e.getMessage());
        }
    }

    public String getJsonString(ArrayList<Category> db) {
        return gson.toJson(db);
    }

    @Override
    public void serialize(ArrayList<Category> db) {
        this.writeToFile(this.getJsonString(db));
    }

    @Override
    public ArrayList<Category> deserialize() {
        TypeToken<ArrayList<Category>> collectionType = new TypeToken<>() {
        };

        return gson.fromJson(readFromFile(), collectionType);
    }
}