package ru.javawebinar.basejava;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Main8_2 {
    public static void main(String[] args) throws IOException {
        File f = new File(".\\");
        browseFiles(0, f);
    }

    private static void browseFiles(int level, File file) throws IOException {
        File[] content = file.listFiles();
        Objects.requireNonNull(content);
        for (File f : content) {
            if (f != null) {
                System.out.println(prepareFileName(level, f.getName()));
                if (f.isDirectory()) {
                    browseFiles(level + 1, f);
                }
            }
        }
    }

    static String prepareFileName(int tabsCount, String fileName) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tabsCount; i++) {
            sb.append('\t');
        }
        sb.append(fileName);
        return sb.toString();
    }
}

