package com.example.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
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
    
    public List<Platform> platformObjs;
    public List<Coin> coinObjs;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        platformObjs = createPlatforms();
        coinObjs = createCoins();
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
        coinObjs.forEach(c -> c.update());
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2d = (Graphics2D)g;

        platformObjs.forEach(p -> p.draw(graphics2d));
        coinObjs.forEach(c -> c.draw(graphics2d));
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

    private List<Platform> createPlatforms() {
        Platform platform = new Platform(700, 710, 800, 50, this);
        Platform platform2 = new Platform(250, 450, 600, 50, this);
        Platform platform3 = new Platform(0, 675, 150, 50, this);
        Platform platform4 = new Platform(1000, 250, 400, 50, this);
        Platform platform5 = new Platform(0, 225, 200, 50, this);
        Platform wall1 = new Platform(0, 0, 50, 896, this);

        return Arrays.asList(platform, platform2, platform3, platform4, platform5, wall1);
    }

    private List<Coin> createCoins() {
        Coin coin1 = new Coin(325, 360, 50, 50, this);
        Coin coin2 = new Coin(525, 360, 50, 50, this);
        Coin coin3 = new Coin(725, 360, 50, 50, this);
        Coin coin4 = new Coin(760, 620, 50, 50, this);
        Coin coin5 = new Coin(960, 620, 50, 50, this);
        Coin coin6 = new Coin(1160, 620, 50, 50, this);
        Coin coin7 = new Coin(75, 585, 50, 50, this);
        Coin coin8 = new Coin(100, 135, 50, 50, this);
        Coin coin9 = new Coin(1045, 160, 50, 50, this);
        Coin coin10 = new Coin(1185, 160, 50, 50, this);

        return new ArrayList<>(Arrays.asList(coin1, coin2, coin3, coin4, coin5, coin6, coin7, coin8, coin9, coin10));
    }
}
