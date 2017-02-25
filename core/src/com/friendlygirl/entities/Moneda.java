package com.friendlygirl.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.friendlygirl.Constants.PIXELS_IN_METER;

public class Moneda extends Actor {

    TextureRegion textureRegion;
    Vector2 posicion;

    public Moneda(TextureRegion textureRegion, Vector2 posicion) {
        this.textureRegion = textureRegion;
        this.posicion = posicion;
        setSize(0.2f * PIXELS_IN_METER, 0.2f * PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(posicion.x * PIXELS_IN_METER, posicion.y * PIXELS_IN_METER);
        batch.draw(textureRegion, getX(), getY(), getWidth(), getHeight());
    }
}
