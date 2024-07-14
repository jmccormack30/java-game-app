package com.example.animations;

import java.awt.image.BufferedImage;

import com.example.entities.Player;

public abstract class PlayerAnimation extends Animation {

    public abstract BufferedImage getImage(Player.Direction direction) throws RuntimeException;
}
