package com.example.androidmediaplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    public static List<File> getListFiles(File parentDir, String extension) {
        ArrayList<File> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file,extension));
            } else {
                if(file.getName().endsWith(extension)){
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }
}
