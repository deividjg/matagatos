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

public class GameOverScreen extends BaseScreen {

    private Stage stage;
    private Image image;
    private Skin skin;
    private TextButton reintentar, menu;
    private Music gameovermusic;

    public GameOverScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/rainbow-ui.json"));
        image = new Image(game.getManager().get("gameover.png", Texture.class));
        reintentar = new TextButton("Reintentar", skin);
        menu = new TextButton("Menu", skin);

        reintentar.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });
        menu.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.menuScreen);
            }
        });

        image.setPosition(320 - image.getWidth()/2, 90);
        reintentar.setSize(350, 70);
        menu.setSize(200, 70);
        reintentar.setPosition(15, 20);
        menu.setPosition(400, 20);

        stage.addActor(image);
        stage.addActor(reintentar);
        stage.addActor(menu);

        gameovermusic = game.getManager().get("audio/gameovermusic.ogg");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        gameovermusic.setLooping(true);
        if(game.preferences.getBoolean("musica")){
            gameovermusic.play();
        }
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        gameovermusic.stop();
    }

    @Override
    public void dispose() {
        stage.dispose();
        gameovermusic.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}
