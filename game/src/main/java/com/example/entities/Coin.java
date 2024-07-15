package com.example.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.example.animations.CoinSpin;
import com.example.core.GamePanel;

public class Coin extends Entity {

    CoinSpin coinSpinAnimation = new CoinSpin();
    
    public Coin(int xPos, int yPos, int width, int height, GamePanel gamePanel) {
        super(xPos, yPos, width, height, null, gamePanel);
    }

    @Override
    public void update() {
        // Coins will be stationary for now
    }

    @Override
    public void draw(Graphics2D g) {
        BufferedImage image = coinSpinAnimation.getImage();
        g.drawImage(image, xPos, yPos, width, height, null);
    }
}
