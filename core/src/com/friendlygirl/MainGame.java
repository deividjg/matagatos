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
    protected WinScreen winScreen;
    protected MenuScreen menuScreen;
    protected MaxPuntScreen maxPuntuacionesScreen;
    protected Preferences preferences;

    @Override
    public void create() {
        manager = new AssetManager();
        manager.load("jugador.png", Texture.class );
        manager.load("suelo.png", Texture.class );
        manager.load("sobresuelo.png", Texture.class );
        manager.load("gato.png", Texture.class );
        manager.load("piedra.png", Texture.class);
        manager.load("logo.png", Texture.class);
        manager.load("gameover.png", Texture.class);
        manager.load("fondo.png", Texture.class);
        manager.load("pumba.png", Texture.class);
        manager.load("audio/gamemusic.ogg", Music.class);
        manager.load("audio/menumusic.ogg", Music.class);
        manager.load("audio/gameovermusic.ogg", Music.class);
        manager.load("audio/maxpuntmusic.ogg", Music.class);
        manager.load("audio/boing.ogg", Sound.class);
        manager.load("audio/grito.ogg", Sound.class);
        manager.load("audio/nivelsuperadomusic.mp3", Music.class);
        manager.load("audio/jabali.ogg", Sound.class);

        manager.finishLoading();

        preferences = Gdx.app.getPreferences("Preferencias");

        gameScreen = new GameScreen(this);
        gameOverScreen = new GameOverScreen(this);
        winScreen = new WinScreen(this);
        menuScreen = new MenuScreen(this);
        maxPuntuacionesScreen = new MaxPuntScreen(this);

        setScreen(menuScreen);
    }

    protected AssetManager getManager() {
        return manager;
    }
}
