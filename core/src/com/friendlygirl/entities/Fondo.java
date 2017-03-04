package com.friendlygirl.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Fondo extends Actor {

    private Texture texture;

    public Fondo(Texture texture) {
        this.texture = texture;
        setSize(640, 360);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(0, 0);
        batch.draw(texture, 0, 0, 640, 360);
    }
}
