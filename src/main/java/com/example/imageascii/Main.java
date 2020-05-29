package com.example.imageascii;

import com.example.imageascii.utils.FileUtils;
import com.example.imageascii.utils.ImageUtils;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Main {

    private static final String DEFAULT_DIALOG_TITLE = "ASCII Converter";
    private static final String SELECT_IMAGE_DIALOG_MSG = "Select image for ASCII conversion.";
    private static final String SAVE_ASCII_DIALOG_MSG = "Save ASCII image to text file.";

    private static final double SCALE_X = .3;
    private static final double SCALE_Y = .3;

    public static void main(String[] args) {
        new Main().start();
    }

    private void start() {
        setSystemLookAndFeel();

        showDialogInformationMessage(SELECT_IMAGE_DIALOG_MSG);

        /*
        FileUtils.chooseOpenImageFile().ifPresentOrElse(openedFile ->
                        ImageUtils.readImageFile(openedFile).ifPresentOrElse(this::generateAsciiFile,
                                () -> System.out.println("Error occurred while reading image file.")),
                () -> System.out.println("Error occurred while selecting file."));
         */
        FileUtils.chooseOpenImageFile()
                .flatMap(ImageUtils::readImageFile)
                .ifPresent(this::generateAsciiFile);
    }

    private void generateAsciiFile(BufferedImage bufferedImage) {
        long start = System.nanoTime();

        bufferedImage = ImageUtils.scaleImage(bufferedImage, SCALE_X, SCALE_Y);

        ImageUtils.convertToGrayscale(bufferedImage);

        String asciiImage = ImageUtils.convertToAscii(bufferedImage);

        long end = System.nanoTime();
        System.out.printf("Time generating ascii: %d%n", (end - start));

        showDialogInformationMessage(SAVE_ASCII_DIALOG_MSG);

        FileUtils.chooseSaveAsciiImageFile(asciiImage);
    }

    private void showDialogInformationMessage(String message) {
        showDialogInformationMessage(message, DEFAULT_DIALOG_TITLE);
    }

    private void showDialogInformationMessage(String message, String title) {
        JOptionPane.showMessageDialog(null,
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
