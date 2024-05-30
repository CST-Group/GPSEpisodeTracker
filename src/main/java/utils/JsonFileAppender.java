package utils;

import br.unicamp.cst.representation.idea.Idea;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonFileAppender {
    String fileName;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public JsonFileAppender(String name) {
        fileName = generateFileName(name);
    }

    private static String generateFileName(String name) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(new Date());
        return name+"_"+timestamp+"_output.txt";
    }

    public void appendJsonToFile(Idea idea) {
        //todo idea to json
        String json = ideaToJson(idea);

        try {
            // Write the JSON content to the new file
            FileWriter fileWriter = new FileWriter(fileName, true);
            fileWriter.write(json + System.lineSeparator());
            fileWriter.close();

//            System.out.println("JSON appended to the file: "+ this.fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String ideaToJson(Idea myIdea) {
        String myJSON = gson.toJson(myIdea);
        JsonObject myJsonObject = gson.fromJson(myJSON, JsonObject.class);
        return myJsonObject.toString();
    }

}