package com.example.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.example.core.GamePanel;
import com.example.image.ImageConstants;
import com.example.image.ImageUtils;

public class Platform extends Entity {

    private BufferedImage image;

    public Platform(int xPos, int yPos, int width, int height, GamePanel gamePanel) {
        super(xPos, yPos, width, height, null, gamePanel);
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.dispose();

        image = ImageUtils.getBufferedImage(ImageConstants.PLATFORM_1);
    }

    @Override
    public void update() {
        // Platforms will be stationary for now
    }

    @Override
    public void draw(Graphics2D g) {
        if (width >= height) {
            for (int i = 0; i < width; i+=50) {
                int x = xPos + i;
                int curWidth = 50;
                int remainingWidth = ((xPos + width) - x);
                if (remainingWidth < 50) {
                    curWidth = remainingWidth;
                }
                g.drawImage(image, x, yPos, curWidth, height, null);
            }
        }
        else {
            for (int i = 0; i < height; i+=50) {
                int y = yPos + i;
                int curHeight = 50;
                int remainingHeight = ((yPos + height) - y);
                if (remainingHeight < 50) {
                    curHeight = remainingHeight;
                }
                g.drawImage(image, xPos, y, width, curHeight, null);
            }
        }
    }
}
