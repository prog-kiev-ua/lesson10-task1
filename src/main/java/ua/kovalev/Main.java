package ua.kovalev;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        try {
            dictionary.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        File file = new File("English.in");
        try {
            dictionary.translateFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
