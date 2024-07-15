package com.example.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.example.core.GamePanel;

public class Platform extends Entity {

    private BufferedImage image;

    public Platform(int xPos, int yPos, int width, int height, GamePanel gamePanel) {
        super(xPos, yPos, width, height, null, gamePanel);
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.dispose();
    }

    @Override
    public void update() {
        // Platforms will be stationary for now
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, xPos, yPos, width, height, null);
    }
}
