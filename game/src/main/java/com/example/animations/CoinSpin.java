package com.example.animations;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.example.image.ImageConstants;
import com.example.image.ImageUtils;

public class CoinSpin extends Animation {

    BufferedImage image1;
    BufferedImage image2;
    BufferedImage image3;
    BufferedImage image4;
    BufferedImage image5;
    BufferedImage image6;
    BufferedImage image7;
    BufferedImage image8;
    protected Map<Integer, BufferedImage> frameImageMap;
    
    public CoinSpin() {
        this.frameIndex = 0;
        this.totalFrames = 42;

        image1 = ImageUtils.getBufferedImage(ImageConstants.COIN_1);
        image2 = ImageUtils.getBufferedImage(ImageConstants.COIN_2);
        image3 = ImageUtils.getBufferedImage(ImageConstants.COIN_3);
        image4 = ImageUtils.getBufferedImage(ImageConstants.COIN_4);
        image5 = ImageUtils.getBufferedImage(ImageConstants.COIN_5);
        image6 = ImageUtils.getBufferedImage(ImageConstants.COIN_6);
        image7 = ImageUtils.getBufferedImage(ImageConstants.COIN_7);
        image8 = ImageUtils.getBufferedImage(ImageConstants.COIN_8);

        frameImageMap = new HashMap<>(){{
            for (int i = 0; i < 5; i++) {
                put(i, image1);
            }
            for (int i = 5; i < 10; i++) {
                put(i, image2);
            }
            for (int i = 10; i < 15; i++) {
                put(i, image3);
            }
            for (int i = 15; i < 20; i++) {
                put(i, image4);
            }
            for (int i = 20; i < 25; i++) {
                put(i, image5);
            }
            for (int i = 25; i < 30; i++) {
                put(i, image6);
            }
            for (int i = 30; i < 35; i++) {
                put(i, image7);
            }
            for (int i = 35; i < 40; i++) {
                put(i, image8);
            }
            for (int i = 40; i < 42; i++) {
                put(i, image1);
            }
        }};
    }

    public BufferedImage getImage() {
        BufferedImage image = frameImageMap.get(frameIndex);
        frameIndex++;
        if (frameIndex >= totalFrames) frameIndex = 0;
        return image;
    }
}
