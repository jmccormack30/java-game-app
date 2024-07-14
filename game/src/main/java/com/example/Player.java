package com.example;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    private int speed = 10;
    private int jumpHeight = 275;

    public enum Direction { LEFT, RIGHT }

    private Direction direction;
    private int jumpEnd;

    private boolean jumping;
    private boolean grounded = true;
    private boolean falling;
    private boolean movingHorizontal;

    private Animation currentAnimation = null;
    private PlayerRun runAnimation;

    private BufferedImage idle_left;
    private BufferedImage idle_right;
    private BufferedImage jump_left;
    private BufferedImage jump_right;
    private BufferedImage run_left;
    private BufferedImage run_right;

    public Player(int xPos, int yPos, KeyHandler keyHandler, GamePanel gamePanel) {
        this(xPos, yPos, 9,  Direction.RIGHT, keyHandler, gamePanel);
    }

    public Player(int xPos, int yPos, int speed, Direction direction, KeyHandler keyHandler, GamePanel gamePanel) {
        super(xPos, yPos, 96, 96, null, keyHandler, gamePanel);
        this.speed = speed;
        this.direction = direction;
        this.idle_left = ImageUtils.getBufferedImage("/player/player_idle_left.png");
        this.idle_right = ImageUtils.getBufferedImage("/player/player_idle_right.png");
        this.jump_left = ImageUtils.getBufferedImage("/player/player_jump_left.png");
        this.jump_right = ImageUtils.getBufferedImage("/player/player_jump_right.png");
        this.run_left = ImageUtils.getBufferedImage("/player/player_run_left.png");
        this.run_right = ImageUtils.getBufferedImage("/player/player_run_right.png");
        runAnimation = new PlayerRun(this);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getJumpHeight() {
        return jumpHeight;
    }

    public void setJumpHeight(int jumpHeight) {
        this.jumpHeight = jumpHeight;
    }

    public void update() {
        // LEFT RIGHT
        int curX = xPos;
        if (keyHandler.leftPressed) {
            direction = Direction.LEFT;
            xPos -= speed;
        }
        if (keyHandler.rightPressed) {
            direction = Direction.RIGHT;
            xPos += speed;
        }
        updateForOutOfBoundsLeftRight();
        movingHorizontal = (curX != xPos);

        // UP DOWN
        int curY = yPos;
        applyGravity();
        updateForOutOfBoundsBelow();
        grounded = (curY == yPos);
        falling = (yPos > curY);

        if (grounded) {
            falling = false;

            if (keyHandler.upPressed) { // player must be on the ground, not jumping or free falling
                jumping = true;
                jumpEnd = (yPos - jumpHeight);
            }
        }
        // if (keyHandler.downPressed) {
        //     yPos += speed;
        // }
        if (jumping) {
            handleJump();
        }
    }

    public void draw(Graphics2D g) {
        BufferedImage currentImage = null;

        if (movingHorizontal && !(jumping || falling)) {
            if (currentAnimation == null) {
                currentAnimation = runAnimation;
                currentAnimation.frameIndex = 0;
            }
        }
        else {
            currentAnimation = null;
        }

        if (currentAnimation != null) {
            currentImage = currentAnimation.getImage(direction);
        }
        else {
            if (direction == Direction.LEFT) {
                if (jumping || falling) {
                    currentImage = jump_left;
                }
                else {
                    if (movingHorizontal) {
                        currentImage = run_left;
                    }
                    else {
                        currentImage = idle_left;
                    }
                }
            }
            else if (direction == Direction.RIGHT) {
                if (jumping || falling) {
                    currentImage = jump_right;
                }
                else {
                    if (movingHorizontal) {
                        currentImage = run_right;
                    }
                    else {
                        currentImage = idle_right;
                    }
                }
            }
        }
        g.drawImage(currentImage, xPos, yPos, width, height, null);
    }

    private void handleJump() {
        if (yPos > jumpEnd+60) {
            yPos -= 29;
        }
        else if (yPos > jumpEnd+30) {
            yPos -= 23;
        }
        else if (yPos > jumpEnd+15) {
            yPos -= 20;
        }
        else {
            jumping = false;
        }
    }

    public BufferedImage getIdleLeft() {
        return idle_left;
    }

    public BufferedImage getIdleRight() {
        return idle_right;
    }

    public BufferedImage getJumpLeft() {
        return jump_left;
    }

    public BufferedImage getJumpRight() {
        return jump_right;
    }

    public BufferedImage getRunLeft() {
        return run_left;
    }

    public BufferedImage getRunRight() {
        return run_right;
    }
}
