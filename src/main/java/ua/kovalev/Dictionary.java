package ua.kovalev;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Dictionary {
    private File file = new File("dictionary.txt");
    private Map<String, String> en_ua = new HashMap<>();

    public void load() throws IOException {
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }

                String[] arr = parse(line);

                if (arr.length == 2) {
                    en_ua.put(arr[0].toLowerCase(), arr[1].toLowerCase());
                    continue;
                }
                // слово может быть без перевода. например артикль
                if (arr.length == 1) {
                    en_ua.put(arr[0].toLowerCase(), null);
                    continue;
                }
            }
        }
    }

    public void translateFile(File file) throws IOException {
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException();
        }
        try (FileInputStream fis = new FileInputStream(file); FileOutputStream fos = new FileOutputStream("Ukrainan.out")) {
            StringBuilder word = new StringBuilder();
            while (fis.available() > 0) {
                int code = fis.read();
                // проверяю символ ли
                if (Character.isLetter(code)) {
                    word.append(Character.toChars(code));
                    if (fis.available() == 0 && word.length() != 0) {
                        String wordTr = translateWordEnToUa(word.toString());
                        // в случае с артиклем, перевод имеет пустую строку. В таком случае не отправляю в файл
                        if(!wordTr.equals("")){
                            // отправляю в файл, предварительно делаю первую букву заглавную если такая была в английском варианте
                            fos.write(checkNeedToUpperCaseFirstLetter(word.toString(), wordTr).getBytes());
                        }
                    }
                } else {
                    if (word.length() != 0 && !word.equals("")) {
                        String wordTr = translateWordEnToUa(word.toString());
                        if(!wordTr.equals("")){
                            fos.write(checkNeedToUpperCaseFirstLetter(word.toString(), wordTr).getBytes());
                        }
                        word.delete(0, word.length());
                    }
                    fos.write(new String(Character.toChars(code)).getBytes());
                }
            }
        }
    }

    public String translateWordEnToUa(String word) {
        // если не находит такого слова в словаре то отправляю обратно английский вариант слова
        if (!en_ua.containsKey(word.toLowerCase())) {
            return word;
        }
        String wordT = en_ua.get(word.toLowerCase());
        if (wordT == null) {
            return "";
        } else {
            return wordT;
        }
    }

    public String[] parse(String line) {
        return line.split("=");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        en_ua.forEach((k, v) -> sb.append(k + " => " + v + "\n"));
        return sb.toString();
    }


    private String checkNeedToUpperCaseFirstLetter(String word, String wordTr) {
        if (wordTr.length() != 0 && Character.isUpperCase(word.charAt(0))) {
            char[] chars = wordTr.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            wordTr = new String(chars);
        }
        return wordTr;
    }
}
