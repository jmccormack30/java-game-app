package com.example;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.example.Player.Direction;

public class PlayerRun extends Animation {

    Player player;

    protected Map<Integer, BufferedImage> frameImageMapLeft;
    protected Map<Integer, BufferedImage> frameImageMapRight;
    
    public PlayerRun(Player player) {
        this.frameIndex = 0;
        this.totalFrames = 18;
        this.player = player;

        frameImageMapLeft = new HashMap<>() {{
            put(0, player.getJumpLeft());
            put(1, player.getJumpLeft());
            put(2, player.getJumpLeft());
            put(3, player.getJumpLeft());
            put(4, player.getJumpLeft());
            put(5, player.getJumpLeft());
            put(6, player.getIdleLeft());
            put(7, player.getIdleLeft());
            put(8, player.getIdleLeft());
            put(9, player.getRunLeft());
            put(10, player.getRunLeft());
            put(11, player.getRunLeft());
            put(12, player.getRunLeft());
            put(13, player.getRunLeft());
            put(14, player.getRunLeft());
            put(15, player.getIdleLeft());
            put(16, player.getIdleLeft());
            put(17, player.getIdleLeft());
        }};

        frameImageMapRight = new HashMap<>() {{
            put(0, player.getJumpRight());
            put(1, player.getJumpRight());
            put(2, player.getJumpRight());
            put(3, player.getJumpRight());
            put(4, player.getJumpRight());
            put(5, player.getJumpRight());
            put(6, player.getIdleRight());
            put(7, player.getIdleRight());
            put(8, player.getIdleRight());
            put(9, player.getRunRight());
            put(10, player.getRunRight());
            put(11, player.getRunRight());
            put(12, player.getRunRight());
            put(13, player.getRunRight());
            put(14, player.getRunRight());
            put(15, player.getIdleRight());
            put(16, player.getIdleRight());
            put(17, player.getIdleRight());
        }};
    }

     public BufferedImage getImage(Direction direction) {
        BufferedImage image = null;
        if (direction == Direction.RIGHT) {
            image = frameImageMapRight.get(frameIndex);
        }
        else {
            image = frameImageMapLeft.get(frameIndex);
        }
        frameIndex++;
        if (frameIndex >= totalFrames) frameIndex = 0;
        return image;
     }
}
