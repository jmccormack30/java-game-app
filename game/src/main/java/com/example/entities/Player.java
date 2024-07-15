package com.example.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.example.animations.PlayerAnimation;
import com.example.animations.PlayerRun;
import com.example.core.GamePanel;
import com.example.core.KeyHandler;
import com.example.image.ImageConstants;
import com.example.image.ImageUtils;

public class Player extends Entity implements Gravitational {

    public enum Direction { LEFT, RIGHT }
    public enum Action { IDLE, RUN, JUMP };

    public static Map<Action, BufferedImage> actionImageMapLeft;
    public static Map<Action, BufferedImage> actionImageMapRight;

    private int speed = 10;
    private int jumpHeight = 275;

    private Direction direction;
    private int jumpEnd;

    private boolean onPlatform = false;

    private boolean jumping;
    private boolean grounded = true;
    private boolean falling;
    private boolean movingHorizontal;

    private PlayerAnimation currentAnimation = null;
    private PlayerRun runAnimation = null;

    static {
        actionImageMapLeft = new HashMap<>();
        actionImageMapRight = new HashMap<>();
        createActionImageMaps(actionImageMapLeft, actionImageMapRight);
    }

    public Player(int xPos, int yPos, KeyHandler keyHandler, GamePanel gamePanel) {
        this(xPos, yPos, 9,  Direction.RIGHT, keyHandler, gamePanel);
    }

    public Player(int xPos, int yPos, int speed, Direction direction, KeyHandler keyHandler, GamePanel gamePanel) {
        super(xPos, yPos, 78, 96, keyHandler, gamePanel);
        this.speed = speed;
        this.direction = direction;
        runAnimation = new PlayerRun();
    }

    @Override
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

        // UP DOWN
        int curY = yPos;
        if (jumping) {
            handleJump();
        }
        applyGravity();
        updateForOutOfBoundsBelow();

        gamePanel.platformObjs.forEach(p -> {
            if (isCollision(p)) {
                updateRelativeToPlatform(p);
            }
        });

        movingHorizontal = (curX != xPos);
        grounded = (curY == yPos);
        falling = (yPos > curY);

        if (grounded) {
            falling = false;

            if (keyHandler.upPressed) { // player must be on the ground, not jumping or free falling
                jumping = true;
                jumpEnd = (yPos - jumpHeight);
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        BufferedImage currentImage = null;

        currentAnimation = getAnimation(currentAnimation);

        if (currentAnimation != null) {
            currentImage = currentAnimation.getImage(direction);
        }
        else {
            Action currentAction = getCurrentAction();
            currentImage = getCurrentImage(currentAction);
        }
        g.drawImage(currentImage, xPos, yPos, width, height, null);
    }

    private PlayerAnimation getAnimation(PlayerAnimation previousFrameAnimation) {
        PlayerAnimation currentFrameAnimation = null;
        // Determine what the animation should be for the current frame

        // RUN
        if (movingHorizontal && !(jumping || falling)) {
            currentFrameAnimation = runAnimation;
        }

        // If animation changes, reset frameIndex, and return new animation
        if (previousFrameAnimation == null) {
            return currentFrameAnimation;
        }
        else if (previousFrameAnimation != currentFrameAnimation) {
            previousFrameAnimation.resetFrameIndex();
            return currentFrameAnimation;
        }
        
        // Else continue with the previous animation
        return previousFrameAnimation;
    }

    private Action getCurrentAction() {
        Action action = Action.IDLE; // DEFAULT

        if (jumping || falling) {
            action = Action.JUMP;
        }
        else {
            if (movingHorizontal) {
                action = Action.RUN;
            }
            else {
                action = Action.IDLE;
            }
        }

        return action;
    }

    private BufferedImage getCurrentImage(Action action) {
        BufferedImage currentImage = null;

        if (direction == Direction.LEFT) {
            currentImage = actionImageMapLeft.get(action);
        }
        else if (direction == Direction.RIGHT) {
            currentImage = actionImageMapRight.get(action);
        }

        return currentImage;
    }

    private static void createActionImageMaps(Map<Action, BufferedImage> actionImageMapLeft, Map<Action, BufferedImage> actionImageMapRight) {
        actionImageMapLeft.put(Action.IDLE, ImageUtils.getBufferedImage(ImageConstants.PLAYER_IDLE_LEFT));
        actionImageMapLeft.put(Action.RUN, ImageUtils.getBufferedImage(ImageConstants.PLAYER_RUN_LEFT));
        actionImageMapLeft.put(Action.JUMP, ImageUtils.getBufferedImage(ImageConstants.PLAYER_JUMP_LEFT));

        actionImageMapRight.put(Action.IDLE, ImageUtils.getBufferedImage(ImageConstants.PLAYER_IDLE_RIGHT));
        actionImageMapRight.put(Action.RUN, ImageUtils.getBufferedImage(ImageConstants.PLAYER_RUN_RIGHT));
        actionImageMapRight.put(Action.JUMP, ImageUtils.getBufferedImage(ImageConstants.PLAYER_JUMP_RIGHT));
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

    public void applyGravity() {
        yPos += 17;
    }

    public boolean isCollision(Platform platform) {
        int playerX = xPos;
        int playerY = yPos;
        int playerWidth = width;
        int playerHeight = height;

        int platformX = platform.getX();
        int platformY = platform.getY();
        int platformWidth = platform.getWidth();
        int platformHeight = platform.getHeight();

        // Player is above platform
        if (playerY + playerHeight <= platformY) {
            return false;
        }

        // Player is below platform
        if (playerY >= platformY + platformHeight) {
            return false;
        }

        // Player is to the left of platform
        if (playerX + playerWidth <= platformX) {
            return false;
        }

        // Player is to the right of platform
        if (playerX >= platformX + platformWidth) {
            return false;
        }

        return true;
    }

    public void updateRelativeToPlatform(Platform platform) {
        int playerX = xPos;
        int playerY = yPos;
        int playerWidth = width;
        int playerHeight = height;

        int platformX = platform.getX();
        int platformY = platform.getY();
        int platformWidth = platform.getWidth();
        int platformHeight = platform.getHeight();

        boolean isLeftSideCollision = isLeftSideCollision(playerX, playerWidth, platformX);
        boolean isRightSideCollision = isRightSideCollision(playerX, playerWidth, platformX, platformWidth);
        boolean isTopSideCollision = isTopSideCollision(playerY, playerHeight, platformY);
        boolean isBottomSideCollision = isBottomSideCollision(playerY, playerHeight, platformY, platformHeight);

        if (isBottomSideCollision && isLeftSideCollision) {
            if (playerY > platformY + platformHeight - 12) {
                yPos = platformY + platformHeight;
                jumping = false;
                return;
            }
            else {
                xPos = platformX + platformWidth;
            }
        }
        else if (isBottomSideCollision && isRightSideCollision) {
            if (playerY > platformY + platformHeight - 12) {
                yPos = platformY + platformHeight;
                jumping = false;
                return;
            }
            else {
                xPos = platformX + platformWidth;
            }
        }
        else if (isBottomSideCollision && jumping) {
            yPos = platformY + platformHeight;
            jumping = false;
            return;
        }

        if (isTopSideCollision && isLeftSideCollision) {
            // move to top
            if (playerY + playerHeight < platformY + 18 && !jumping) {
                yPos = platformY - playerHeight;
            }
            // move to left
            else {
                xPos = platformX - playerWidth;
            }
        }
        else if (isTopSideCollision && isRightSideCollision) {
            // move to top
            if (playerY + playerHeight < platformY + 18 && !jumping) {
                yPos = platformY - playerHeight;
            }
            // move to right
            else {
                xPos = platformX + platformWidth;
            }
        }
        else if (isTopSideCollision && !jumping) {
            yPos = platformY - playerHeight;
        }
        else if (isLeftSideCollision) {
            xPos = platformX - playerWidth;
        }
        else if (isRightSideCollision) {
            xPos = platformX + platformWidth;
        }
    }

    public boolean isLeftSideCollision(int playerX, int playerWidth, int platformX) {
        return ((playerX < platformX) && (playerX + playerWidth > platformX));
    }

    public boolean isRightSideCollision(int playerX, int playerWidth, int platformX, int platformWidth) {
        return ((playerX < platformX + platformWidth) && (playerX + playerWidth > platformX + platformWidth));
    }

    public boolean isTopSideCollision(int playerY, int playerHeight, int platformY) {
        return ((playerY < platformY) && (playerY + playerHeight > platformY));
    }

    public boolean isBottomSideCollision(int playerY, int playerHeight, int platformY, int platformHeight) {
        return ((playerY < platformY + platformHeight) && (playerY + playerHeight > platformY + platformHeight));
    }
}
