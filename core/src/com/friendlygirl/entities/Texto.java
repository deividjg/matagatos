package com.friendlygirl.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Texto extends Actor {

    private BitmapFont font;

    private String texto;
    private Vector2 posicion;

    public Texto(String texto, Vector2 posicion) {
        font = new BitmapFont(Gdx.files.internal("fuentes/font.fnt"), Gdx.files.internal("fuentes/font.png"), false);
        this.texto = texto;
        this.posicion = posicion;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, texto, posicion.x, posicion.y);
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
