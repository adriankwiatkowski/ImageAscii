package com.example.imageascii.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ImageUtils {

    private static final char[] GRAYSCALE_REPRESENTATION =
            new char[]{'@', '%', '#', '*', '+', '=', '-', ':', '.'};
    //private static final String GRAYSCALE_STRING_REPRESENTATION =
    //        "\"$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/|()1{}[]?-_+~<>i!lI;:,\"^`'. \"";
    //private static final String GRAYSCALE_STRING_REPRESENTATION = " .:-=+*#%@";

    private ImageUtils() {
    }

    public static String convertToAscii(BufferedImage grayscaleImage) {
        final int width = grayscaleImage.getWidth();
        final int height = grayscaleImage.getHeight();
        StringBuilder sb = new StringBuilder((width / 2) + width * height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = grayscaleImage.getRGB(x, y);

                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb) & 0xFF;

                float avg = ((r + g + b) / 3f) / 255f;
                int index = (int) (avg * (GRAYSCALE_REPRESENTATION.length - 1));
                index = Math.min(index, GRAYSCALE_REPRESENTATION.length - 1);

                char c = GRAYSCALE_REPRESENTATION[index];

                sb.append(c).append(c);
                if (x % 2 == 0) {
                    sb.append(c);
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void convertToGrayscale(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                Color color = new Color(image.getRGB(x, y));

                int r = (int) (color.getRed() * 0.2126f);
                int g = (int) (color.getGreen() * 0.7152f);
                int b = (int) (color.getBlue() * 0.0722f);

                color = new Color(r + g + b, r + g + b, r + g + b);

                image.setRGB(x, y, color.getRGB());
            }
        }
    }

    public static BufferedImage scaleImage(BufferedImage srcImage,
                                           double scaleX,
                                           double scaleY) {
        final int width = (int) (srcImage.getWidth() * scaleX);
        final int height = (int) (srcImage.getHeight() * scaleY);
        BufferedImage scaledImage =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final AffineTransform at = AffineTransform.getScaleInstance(scaleX, scaleY);
        final AffineTransformOp ato =
                new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        scaledImage = ato.filter(srcImage, scaledImage);
        return scaledImage;
    }

    public static Optional<BufferedImage> readImageFile(File imageFile) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(bufferedImage);
    }
}
