package com.friendlygirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.friendlygirl.entities.Puntos;
import com.friendlygirl.entities.Texto;

import java.util.ArrayList;

public class MaxPuntScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;
    private TextButton volver;
    private Texto titulo;
    protected ArrayList<Texto> listaPunt;

    public MaxPuntScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        titulo = new Texto("PUNTUACIONES MAXIMAS", new Vector2(140, 340));

        puntosPorDefecto();

        listaPunt = new ArrayList<Texto>();
        for(int i = 1; i <= 5; i++) {
            listaPunt.add(new Texto(i + "º    " + game.preferences.getInteger(i + ""), new Vector2(220, 320 - 30*i )));
        }

        volver = new TextButton("Volver", skin);
        volver.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.menuScreen);
            }
        });

        volver.setSize(150, 70);
        volver.setPosition(320 - volver.getWidth()/2, 20);

        stage.addActor(titulo);

        for(Texto texto : listaPunt){
            stage.addActor(texto);
        }

        stage.addActor(volver);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    protected  void actualiza(){
        for(int i = 1; i <= 5; i++){
            listaPunt.get(i-1).setTexto(i + "º    " + game.preferences.getInteger(i + ""));
        }
        game.preferences.flush();
    }

    protected void puntosPorDefecto(){
        if(!game.preferences.contains("i")){
            for(int i=1; i<=5; i++) {
                game.preferences.putInteger(i + "", 0);
            }
        }
    }
}
