package com.friendlygirl.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Puntos extends Actor {

    private BitmapFont font;
    private int puntos;

    public Puntos() {
        font = new BitmapFont(Gdx.files.internal("fuentes/font.fnt"), Gdx.files.internal("fuentes/font.png"), false);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, "Puntos: " + puntos, 50, 350);
        puntos = 0;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
