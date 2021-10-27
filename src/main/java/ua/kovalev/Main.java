package ua.kovalev;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите слово на английском: ");
        String wordEn = scanner.next();
        System.out.println("Введите слово на украинском: ");
        String wordUa = scanner.next();
        dictionary.addWord(wordEn, wordUa);

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
