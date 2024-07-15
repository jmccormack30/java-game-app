package com.example.entities;

import java.awt.Graphics2D;

import com.example.core.GamePanel;
import com.example.core.KeyHandler;

public abstract class Entity {
    protected int xPos;
    protected int yPos;
    protected int width;
    protected int height;
    protected KeyHandler keyHandler;
    protected GamePanel gamePanel;

    public Entity(int xPos, int yPos, int width, int height, GamePanel gamePanel) {
        this(xPos, yPos, width, height, null, gamePanel);
    }

    public Entity(int xPos, int yPos, int width, int height, KeyHandler keyHandler, GamePanel gamePanel) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.keyHandler = keyHandler;
        this.gamePanel = gamePanel;
    }

    public abstract void update();

    public abstract void draw(Graphics2D g);

    public int getX() {
        return xPos;
    }

    public void setX(int xPos) {
        this.xPos = xPos;
    }

    public int getY() {
        return yPos;
    }

    public void setY(int yPos) {
        this.yPos = yPos;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public void setKeyHandler(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void updateForOutOfBoundsLeftRight() {
        if (xPos < 0) {
            xPos = 0;
        }
        if (xPos > (gamePanel.screenWidth - this.width)) {
            xPos = (gamePanel.screenWidth - this.width);
        }
    }

    public void updateForOutOfBoundsBelow() {
        if (yPos > (gamePanel.screenHeight - this.height)) {
            yPos = (gamePanel.screenHeight - this.height);
        }
    }
}
