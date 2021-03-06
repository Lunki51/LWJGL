package fr.lunki.lwjgl.engine.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FileUtils {

    public static String loadAsString(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtils.class.getResourceAsStream("/" + path)));
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("File not found : " + path);
        }
        return builder.toString();
    }

}
