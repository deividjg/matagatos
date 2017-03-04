package com.friendlygirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.friendlygirl.entities.Fondo;
import com.friendlygirl.entities.Gato;
import com.friendlygirl.entities.Piedra;
import com.friendlygirl.entities.Pumba;
import com.friendlygirl.entities.Suelo;
import com.friendlygirl.entities.Jugador;
import com.friendlygirl.entities.Puntos;

import java.util.ArrayList;
import java.util.Collections;

import static com.friendlygirl.Constantes.PIXELS_EN_METROS;

public class GameScreen extends BaseScreen {

    private Stage stage;
    private World world;
    private Jugador jugador;
    private Pumba pumba;
    private Gato gato;
    private Fondo fondo;
    private ArrayList<Suelo> listaSuelos = new ArrayList<Suelo>();
    private ArrayList<Piedra> listaPiedras = new ArrayList<Piedra>();
    private Music gameMusic;
    private Sound grito, boing, jabali;
    protected Boolean acudePumba = false;

    public GameScreen(final MainGame game) {
        super(game);
        gameMusic = game.getManager().get("audio/gamemusic.ogg");
        grito = game.getManager().get("audio/grito.ogg");
        boing = game.getManager().get("audio/boing.ogg");
        jabali = game.getManager().get("audio/jabali.ogg");
        stage = new Stage(new FitViewport(640, 360));
        world = new World(new Vector2(0, -10), true);

        world.setContactListener(new ContactListener() {

            private boolean areCollided(Contact contact, Object userA, Object userB){
                if(contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB) ||
                        contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA)){
                    return true;
                }else{
                    return false;
                }
            }

            @Override
            public void beginContact(Contact contact) {
                if(areCollided(contact, "jugador", "suelo")){
                    jugador.setSaltando(false);
                }

                if(areCollided(contact, "jugador", "pumba")){
                    if(jugador.isVivo()){
                        muere();
                    }
                }

                if(areCollided(contact, "piedra", "gato")){
                    acudePumba = true;
                    jugador.setAcudePumba(true);
                }
            }

            @Override
            public void endContact(Contact contact) {
                if(areCollided(contact, "jugador", "suelo")) {
                    if (jugador.isVivo()) {
                        if(jugador.isSaltando()) {
                            boing.play();
                        }
                    }
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });
    }

    @Override
    public void show() {
        Texture texturePumba = game.getManager().get("pumba.png");
        pumba = new Pumba(world, texturePumba, new Vector2(15, 1));
        Texture textureJugador = game.getManager().get("jugador.png");
        Texture textureGato = game.getManager().get("gato.png");
        Texture textureSuelo = game.getManager().get("suelo.png");
        Texture textureSobresuelo = game.getManager().get("sobresuelo.png");
        Texture textureFondo = game.getManager().get("fondo.png");

        listaSuelos.add(new Suelo(world, textureSuelo, textureSobresuelo, -10, 50, 0.5f));

        fondo = new Fondo(textureFondo);
        jugador = new Jugador(world, textureJugador, new Vector2(2, 1));
        gato = new Gato(world, textureGato, new Vector2(4, 3f));

        stage.addActor(fondo);

        for(Suelo suelo : listaSuelos){
            stage.addActor(suelo);
        }

        stage.addActor(jugador);
        stage.addActor(gato);

        gameMusic.setLooping(true);
        if(game.preferences.getBoolean("musica")){
            gameMusic.play();
        }
    }

    @Override
    public void hide() {
        stage.clear();
        jugador.detach();
        for(Suelo suelo : listaSuelos){
            suelo.detach();
        }
        for(Piedra piedra : listaPiedras){
            piedra.detach();
        }

        pumba.detach();
        gato.detach();

        gameMusic.stop();
        listaSuelos.clear();
        listaPiedras.clear();
        acudePumba = false;
        jugador.setAcudePumba(false);
        jugador.setVivo(true);
        gameMusic.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.9f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.justTouched()){
            Texture texturePiedra = game.getManager().get("piedra.png");
            listaPiedras.add(new Piedra(world, texturePiedra, new Vector2((jugador.getX() + 150) / PIXELS_EN_METROS, (jugador.getY() + 150) / PIXELS_EN_METROS)));
            stage.addActor(listaPiedras.get(listaPiedras.size()-1));
        }

        if (acudePumba){
            acudePumba();
            acudePumba = false;
        }

        stage.getCamera().position.set(stage.getWidth()/3 + jugador.getX(), stage.getHeight()/2, 0);
        nivelCompletado();
        stage.act();
        world.step(delta, 6, 2);
        stage.draw();
    }

    private void muere() {
        jugador.setVivo(false);
        grito.play();
        stage.addAction(Actions.sequence(Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(game.gameOverScreen);
            }
        })));
    }

    private void nivelCompletado(){
        if(jugador.isVivo() && pumba.getX() < -400){
            jugador.setVivo(false);
            stage.addAction(Actions.sequence(Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(game.winScreen);
                }
            })));
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        gameMusic.dispose();
        grito.dispose();
        boing.dispose();
    }

    protected void acudePumba(){
        stage.addActor(pumba);
        jabali.play();
    }


}
