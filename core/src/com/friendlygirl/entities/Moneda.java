package com.friendlygirl.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.friendlygirl.Constantes.PIXELS_EN_METROS;

public class Moneda extends Actor {

    TextureRegion textureRegion;
    Vector2 posicion;
    boolean disponible;

    public Moneda(TextureRegion textureRegion, Vector2 posicion) {
        this.textureRegion = textureRegion;
        this.posicion = posicion;
        disponible = true;
        setSize(0.2f * PIXELS_EN_METROS, 0.2f * PIXELS_EN_METROS);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(posicion.x * PIXELS_EN_METROS, posicion.y * PIXELS_EN_METROS);
        batch.draw(textureRegion, getX(), getY(), getWidth(), getHeight());
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
