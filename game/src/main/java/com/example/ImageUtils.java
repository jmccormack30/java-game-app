package com.example;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {
    
    public static BufferedImage getBufferedImage(String imageFilePath) {
        BufferedImage img = null;

        if (imageFilePath != null) {
            try {
                img = ImageIO.read(ImageUtils.class.getResourceAsStream(imageFilePath));
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        return img;
    }
}
