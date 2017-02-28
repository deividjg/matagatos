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

import javax.sound.midi.SysexMessage;

public class MenuScreen extends BaseScreen {

    private Stage stage;
    private Image image;
    private Skin skin;
    private TextButton inicio, puntos, salir, musicOnOff;
    private Music menumusic;

    public MenuScreen(final MainGame game) {
        super(game);

        if(!game.preferences.contains("musica")){
            game.preferences.putBoolean("musica", true);
            game.preferences.flush();
        }

        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/rainbow-ui.json"));

        image = new Image(game.getManager().get("logo.png", Texture.class));
        inicio = new TextButton("Inicio", skin);
        puntos = new TextButton("Puntos", skin);
        salir = new TextButton("Salir", skin);
        musicOnOff = new TextButton("Musica", skin);

        inicio.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });
        puntos.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.maxPuntuacionesScreen);
            }
        });
        salir.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        musicOnOff.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.preferences.getBoolean("musica")){
                    game.preferences.putBoolean("musica", false);
                    menumusic.stop();
                }else{
                    game.preferences.putBoolean("musica", true);
                    menumusic.play();
                }
                game.preferences.flush();
            }
        });

        image.setSize(200, 200);
        image.setPosition(320 - image.getWidth()/2, 160);
        inicio.setSize(200, 70);
        puntos.setSize(240, 70);
        salir.setSize(180, 70);
        musicOnOff.setSize(250, 70);
        inicio.setPosition(5, 120);
        puntos.setPosition(5, 20);
        salir.setPosition(430, 120);
        musicOnOff.setPosition(360, 20);

        stage.addActor(image);
        stage.addActor(inicio);
        stage.addActor(puntos);
        stage.addActor(salir);
        stage.addActor(musicOnOff);

        menumusic = game.getManager().get("audio/menumusic.ogg");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        menumusic.setLooping(true);
        if(game.preferences.getBoolean("musica")){
            menumusic.play();
        }
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