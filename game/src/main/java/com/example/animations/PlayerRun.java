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
        this.totalFrames = 18;

        frameActionMap = new HashMap<>() {{
            put(0, Player.Action.JUMP);
            put(1, Player.Action.JUMP);
            put(2, Player.Action.JUMP);
            put(3, Player.Action.JUMP);
            put(4, Player.Action.JUMP);
            put(5, Player.Action.JUMP);

            put(6, Player.Action.IDLE);
            put(7, Player.Action.IDLE);
            put(8, Player.Action.IDLE);

            put(9, Player.Action.RUN);
            put(10, Player.Action.RUN);
            put(11, Player.Action.RUN);
            put(12, Player.Action.RUN);
            put(13, Player.Action.RUN);
            put(14, Player.Action.RUN);

            put(15, Player.Action.IDLE);
            put(16, Player.Action.IDLE);
            put(17, Player.Action.IDLE);
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
