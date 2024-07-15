package com.example.animations;

import java.awt.image.BufferedImage;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.example.entities.Player;

public class PlayerRun extends PlayerAnimation {

    protected Map<Integer, Player.Action> frameActionMap;
    
    public PlayerRun() {
        this.frameIndex = 0;
        this.totalFrames = 28;

        frameActionMap = new HashMap<>() {{
            for (int i = 0; i < 9; i++) {
                put(i, Player.Action.JUMP);
            }
            for (int i = 9; i < 14; i++) {
                put(i, Player.Action.IDLE);
            }
            for (int i = 14; i < 23; i++) {
                put(i, Player.Action.RUN);
            }
            for (int i = 23; i < 28; i++) {
                put(i, Player.Action.IDLE);
            }
        }};
    }

    @Override
    public BufferedImage getImage(Player.Direction direction) throws RuntimeException {
        Player.Action currentAction = frameActionMap.get(frameIndex);
        BufferedImage image = null;

        if (direction == Player.Direction.RIGHT) {
            image = Player.actionImageMapRight.get(currentAction);
        }
        else if (direction == Player.Direction.LEFT) {
            image = Player.actionImageMapLeft.get(currentAction);
        }
        else {
            throw new RuntimeException(MessageFormat.format("No valid image found for PlayerRun animation with frameIndex {0} and action {1}", frameIndex, currentAction));
        }

        frameIndex++;
        if (frameIndex >= totalFrames) frameIndex = 0;
        return image;
    }
}
