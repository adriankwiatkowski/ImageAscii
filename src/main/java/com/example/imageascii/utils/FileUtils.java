package com.example.imageascii.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class FileUtils {

    private static final String IMAGE_FILE_DESCRIPTION = "Images " +
            "('JPEG', ('*.jpg','*.jpeg','*.jpe','*.jfif'))," +
            "('PNG', '*.png')," +
            "('BMP', ('*.bmp','*.jdib'))," +
            "('GIF', '*.gif')";
    private static final String[] IMAGE_FILE_EXTENSIONS =
            new String[]{"jpg", "jpeg", ".jfif", "png", "bmp", "jdib", "gif"};

    private static final String IMAGE_ASCII_DESCRIPTION = "Text file ('TEXT', '.txt')";
    private static final String[] IMAGE_ASCII_EXTENSIONS = new String[]{".txt"};

    private FileUtils() {
    }

    public static Optional<File> chooseOpenImageFile() {
        File chosenFile = null;

        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter imageFileFilter =
                new FileNameExtensionFilter(IMAGE_FILE_DESCRIPTION, IMAGE_FILE_EXTENSIONS);

        fileChooser.setFileFilter(imageFileFilter);


        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            chosenFile = fileChooser.getSelectedFile();
        }
        return Optional.ofNullable(chosenFile);
    }

    public static void chooseSaveAsciiImageFile(String asciiImage) {
        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter imageFileFilter =
                new FileNameExtensionFilter(IMAGE_ASCII_DESCRIPTION, IMAGE_ASCII_EXTENSIONS);

        fileChooser.setFileFilter(imageFileFilter);


        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getPath();
            boolean isExtensionFound = isExtensionFound(filePath, IMAGE_ASCII_EXTENSIONS);
            if (!isExtensionFound) {
                if (filePath.endsWith(".")) {
                    filePath = filePath.substring(0, filePath.lastIndexOf("."));
                }
                file = new File(filePath + ".txt");
                filePath = file.getPath();
            }

            writeTextToFile(file, asciiImage);
        }
    }

    public static void writeTextToFile(File file, String text) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getPath()))) {
            bufferedWriter.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isExtensionFound(String filePath, String[] extensions) {
        int i = filePath.lastIndexOf('.');
        if (i > 0 && i < filePath.length() - 1) {
            String currentExtension = filePath.substring(i + 1);
            for (String extension : extensions) {
                if (currentExtension.endsWith(extension)) {
                    return true;
                }
            }
        }
        return false;
    }
}
