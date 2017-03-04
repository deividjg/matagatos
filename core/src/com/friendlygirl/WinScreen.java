package com.friendlygirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.friendlygirl.entities.Texto;

public class WinScreen extends BaseScreen {
    private Stage stage;
    private Texto texto;
    private Skin skin;
    private TextButton rejugar, menu;
    private Music nivelsuperadomusic;

    public WinScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/rainbow-ui.json"));

        texto = new Texto("¡Bien, Esquivaste a ese maldito jabalí!", new Vector2(20, 250));
        rejugar = new TextButton("Rejugar", skin);
        menu = new TextButton("Menu", skin);
        rejugar.addCaptureListener(new ChangeListener() {
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

        rejugar.setSize(270, 70);
        menu.setSize(200, 70);
        rejugar.setPosition(50, 20);
        menu.setPosition(540 - menu.getWidth(), 20);

        stage.addActor(texto);
        stage.addActor(rejugar);
        stage.addActor(menu);

        nivelsuperadomusic = game.getManager().get("audio/nivelsuperadomusic.mp3");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        nivelsuperadomusic.play();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        nivelsuperadomusic.stop();
    }

    @Override
    public void dispose() {
        stage.dispose();
        nivelsuperadomusic.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}
