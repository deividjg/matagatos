package com.friendlygirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.Collections;

public class MenuScreen extends BaseScreen {

    private Stage stage;
    private Image image;
    private Skin skin;
    private TextButton inicio, puntos, salir;
    private Music menumusic;

    public MenuScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/rainbow-ui.json"));

        image = new Image(game.getManager().get("logo.png", Texture.class));
        inicio = new TextButton("Inicio", skin);
        puntos = new TextButton("Puntos", skin);
        salir = new TextButton("Salir", skin);
        inicio.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });
        puntos.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.maxPuntuaciones);
            }
        });
        salir.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        image.setPosition(320 - image.getWidth()/2, 120);
        inicio.setSize(150, 70);
        puntos.setSize(150, 70);
        salir.setSize(150, 70);
        inicio.setPosition(10, 20);
        puntos.setPosition(250, 20);
        salir.setPosition(470, 20);

        stage.addActor(image);
        stage.addActor(inicio);
        stage.addActor(puntos);
        stage.addActor(salir);

        menumusic = game.getManager().get("audio/menumusic.ogg");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        menumusic.setLooping(true);
        menumusic.play();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        menumusic.stop();
    }

    @Override
    public void dispose() {
        stage.dispose();
        menumusic.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}