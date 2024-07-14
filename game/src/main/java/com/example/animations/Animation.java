package com.example.animations;

public abstract class Animation {
    protected int frameIndex;
    protected int totalFrames;
    protected boolean active;

    public void resetFrameIndex() {
        frameIndex = 0;
    }
}
