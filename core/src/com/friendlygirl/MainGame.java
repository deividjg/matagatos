package com.friendlygirl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class MainGame extends Game {

    private AssetManager manager;
    protected GameScreen gameScreen;
    protected GameOverScreen gameOverScreen;
    protected MenuScreen menuScreen;
    protected MaxPuntScreen maxPuntuaciones;
    protected Preferences preferences;

    @Override
    public void create() {
        manager = new AssetManager();
        manager.load("girl.png", Texture.class );
        manager.load("floor.png", Texture.class );
        manager.load("overfloor.png", Texture.class );
        manager.load("spikeblood.png", Texture.class );
        manager.load("spikebloodinverse.png", Texture.class);
        manager.load("logo.png", Texture.class);
        manager.load("gameover.png", Texture.class);
        manager.load("moneda.png", Texture.class);
        manager.load("audio/gamemusic.ogg", Music.class);
        manager.load("audio/menumusic.ogg", Music.class);
        manager.load("audio/gameovermusic.ogg", Music.class);
        manager.load("audio/maxpuntmusic.ogg", Music.class);
        manager.load("audio/boing.ogg", Sound.class);
        manager.load("audio/grito.ogg", Sound.class);
        manager.load("audio/moneda.mp3", Sound.class);
        manager.finishLoading();

        preferences = Gdx.app.getPreferences("Preferencias");

        gameScreen = new GameScreen(this);
        gameOverScreen = new GameOverScreen(this);
        menuScreen = new MenuScreen(this);
        maxPuntuaciones = new MaxPuntScreen(this);

        setScreen(menuScreen);
    }

    protected AssetManager getManager() {
        return manager;
    }
}
