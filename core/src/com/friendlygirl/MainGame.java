package com.friendlygirl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class MainGame extends Game {

    private AssetManager manager;
    public GameScreen gameScreen;
    public GameOverScreen gameOverScreen;

    @Override
    public void create() {
        manager = new AssetManager();
        manager.load("girl.png", Texture.class );
        manager.load("floor.png", Texture.class );
        manager.load("overfloor.png", Texture.class );
        manager.load("spike.png", Texture.class );
        manager.load("gameover.png", Texture.class);
        manager.load("audio/gamemusic.ogg", Music.class);
        manager.load("audio/boing.ogg", Sound.class);
        manager.load("audio/grito.ogg", Sound.class);
        manager.finishLoading();

        gameScreen = new GameScreen(this);
        gameOverScreen = new GameOverScreen(this);

        setScreen(new GameScreen(this));

    }

    public AssetManager getManager() {
        return manager;
    }
}
