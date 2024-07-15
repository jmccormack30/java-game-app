package com.example.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.example.entities.Coin;
import com.example.entities.Platform;
import com.example.entities.Player;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 32;
    final int scale = 2;

    final int tileSize = (originalTileSize * scale); // each tile is 64x64
    final int maxScreenCol = 20;
    final int maxScreenRow = 14;

    public final int screenWidth = (tileSize * maxScreenCol);
    public final int screenHeight = (tileSize * maxScreenRow);

    // FPS
    int fps = 60;

    // STARTING POSITION
    int playerX = 65;
    int playerY = 799;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    Player player = new Player(playerX, playerY, keyHandler, this);
    Coin coin1 = new Coin(325, 270, 50, 50, this);
    Coin coin2 = new Coin(525, 270, 50, 50, this);
    Coin coin3 = new Coin(725, 270, 50, 50, this);
    
    public List<Platform> platformObjs;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        Platform platform = new Platform(700, 710, 800, 50, this);
        Platform platform2 = new Platform(250, 450, 600, 50, this);
        Platform platform3 = new Platform(0, 675, 150, 50, this);
        Platform platform4 = new Platform(1000, 250, 400, 50, this);
        Platform platform5 = new Platform(0, 225, 200, 50, this);
        Platform wall1 = new Platform(0, 0, 50, 896, this);
        platformObjs = new ArrayList<>();
        platformObjs.add(platform);
        platformObjs.add(platform2);
        platformObjs.add(platform3);
        platformObjs.add(platform4);
        platformObjs.add(platform5);
        platformObjs.add(wall1);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        
        double drawInterval = (1000000000 / fps); // 1/60 of a second
        double nextDrawTime = (System.nanoTime() + drawInterval);

        while (gameThread != null) {
            // 1 UPDATE: update game data
            update();

            // 2 DRAW: draw to the screen based on updated data
            repaint();

            // 3 WAIT: sleep until the next time we need to update and draw
            nextDrawTime = sleep(nextDrawTime, drawInterval);
        }
    }

    public void update() {
        platformObjs.forEach(p -> p.update());
        coin1.update();
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2d = (Graphics2D)g;
        platformObjs.forEach(p -> p.draw(graphics2d));
        coin1.draw(graphics2d);
        coin2.draw(graphics2d);
        coin3.draw(graphics2d);
        player.draw(graphics2d);

        graphics2d.dispose();
    }

    public double sleep(double nextDrawTime, double drawInterval) {
        try {
            double remainingTime = (nextDrawTime - System.nanoTime());
            remainingTime /= 1000000; // convert nanoseconds to milliseconds
            if (remainingTime < 0) {
                remainingTime = 0;
            }
            Thread.sleep((long) remainingTime);

            nextDrawTime = (System.nanoTime() + drawInterval);
            return nextDrawTime;
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
            return 0;
        }
    }
}
