package com.example;

import java.awt.image.BufferedImage;

import com.example.Player.Direction;

public abstract class Animation {
    protected int frameIndex;
    protected int totalFrames;
    protected boolean active;

    public void setActive(boolean active) {
        this.active = active;
    }

    public abstract BufferedImage getImage(Direction direction);
}
