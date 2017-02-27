package com.friendlygirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.friendlygirl.entities.Suelo;
import com.friendlygirl.entities.Jugador;
import com.friendlygirl.entities.Moneda;
import com.friendlygirl.entities.PinchoMovil;
import com.friendlygirl.entities.Puntos;
import com.friendlygirl.entities.Pincho;

import java.util.ArrayList;
import java.util.Collections;

public class GameScreen extends BaseScreen {

    private Stage stage;
    private World world;
    private Puntos puntos;
    private Jugador jugador;
    private ArrayList<Pincho> listaPinchos = new ArrayList<Pincho>();
    private ArrayList<Suelo> listaSuelos = new ArrayList<Suelo>();
    private ArrayList<Moneda> listaMonedas = new ArrayList<Moneda>();
    private ArrayList<PinchoMovil> listaPinchosMoviles = new ArrayList<PinchoMovil>();
    private Music gameMusic;
    private Sound grito, boing, sonidoMoneda;

    public GameScreen(final MainGame game) {
        super(game);
        puntos = new Puntos(new Vector2(50, 350));
        gameMusic = game.getManager().get("audio/gamemusic.ogg");
        grito = game.getManager().get("audio/grito.ogg");
        boing = game.getManager().get("audio/boing.ogg");
        sonidoMoneda = game.getManager().get("audio/moneda.mp3");
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

                if(areCollided(contact, "jugador", "pincho")){
                    if(jugador.isVivo()){
                        muere();
                    }
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
        Texture textureJugador = game.getManager().get("jugador.png");
        TextureRegion regionJugador = new TextureRegion(textureJugador, 0, 0, 140, 185);
        Texture textureSuelo = game.getManager().get("suelo.png");
        Texture textureSobresuelo = game.getManager().get("sobresuelo.png");
        Texture texturePincho = game.getManager().get("pincho.png");
        Texture monedaTexture = game.getManager().get("moneda.png");
        TextureRegion monedaRegion = new TextureRegion(monedaTexture, 0, 0, 290, 348);
        Texture pinchoMovilTexture = game.getManager().get("pinchoinverso.png");

        listaSuelos.add(new Suelo(world, textureSuelo, textureSobresuelo, -10, 28, 1));
        listaSuelos.add(new Suelo(world, textureSuelo, textureSobresuelo, 8, 6, 2));
        listaSuelos.add(new Suelo(world, textureSuelo, textureSobresuelo, 20, 20, 1));
        listaSuelos.add(new Suelo(world, textureSuelo, textureSobresuelo, 42, 20, 1));
        listaSuelos.add(new Suelo(world, textureSuelo, textureSobresuelo, 52, 8, 2));
        listaSuelos.add(new Suelo(world, textureSuelo, textureSobresuelo, 66, 50, 1));
        listaPinchos.add(new Pincho(world, texturePincho, 6, 1));
        listaPinchos.add(new Pincho(world, texturePincho, 11, 2));
        listaPinchos.add(new Pincho(world, texturePincho, 31, 1));
        listaPinchos.add(new Pincho(world, texturePincho, 32, 1));
        listaPinchos.add(new Pincho(world, texturePincho, 36, 1));
        listaPinchos.add(new Pincho(world, texturePincho, 37, 1));
        listaPinchos.add(new Pincho(world, texturePincho, 43, 1));
        listaPinchos.add(new Pincho(world, texturePincho, 46, 1));
        listaPinchos.add(new Pincho(world, texturePincho, 55, 2));
        listaPinchos.add(new Pincho(world, texturePincho, 56, 2));
        listaPinchos.add(new Pincho(world, texturePincho, 70, 1));
        listaPinchosMoviles.add(new PinchoMovil(world, pinchoMovilTexture, 4, 1));
        listaPinchosMoviles.add(new PinchoMovil(world, pinchoMovilTexture, 25, 1));
        listaPinchosMoviles.add(new PinchoMovil(world, pinchoMovilTexture, 26, 1));
        listaPinchosMoviles.add(new PinchoMovil(world, pinchoMovilTexture, 50, 1));
        listaPinchosMoviles.add(new PinchoMovil(world, pinchoMovilTexture, 61, 1));
        listaPinchosMoviles.add(new PinchoMovil(world, pinchoMovilTexture, 73, 1));
        listaMonedas.add(new Moneda(monedaRegion, new Vector2(5.9f, 2.5f)));
        listaMonedas.add(new Moneda(monedaRegion, new Vector2(10.9f, 3)));
        listaMonedas.add(new Moneda(monedaRegion, new Vector2(18.5f, 2)));
        listaMonedas.add(new Moneda(monedaRegion, new Vector2(19.5f, 2)));
        listaMonedas.add(new Moneda(monedaRegion, new Vector2(25, 1)));
        listaMonedas.add(new Moneda(monedaRegion, new Vector2(26, 1)));
        listaMonedas.add(new Moneda(monedaRegion, new Vector2(31, 3)));
        listaMonedas.add(new Moneda(monedaRegion, new Vector2(32, 3)));
        listaMonedas.add(new Moneda(monedaRegion, new Vector2(36, 3)));
        listaMonedas.add(new Moneda(monedaRegion, new Vector2(37, 3)));
        listaMonedas.add(new Moneda(monedaRegion, new Vector2(51, 2)));
        listaMonedas.add(new Moneda(monedaRegion, new Vector2(56, 3)));
        listaMonedas.add(new Moneda(monedaRegion, new Vector2(57, 3)));
        listaMonedas.add(new Moneda(monedaRegion, new Vector2(61.5f, 1)));
        for(int i = 0; i < 4; i++){
            listaMonedas.add(new Moneda(monedaRegion, new Vector2(66+i, 2)));
        }

        jugador = new Jugador(world, regionJugador, new Vector2(2, 1.5f));

        for(Suelo suelo : listaSuelos){
            stage.addActor(suelo);
        }
        for(Pincho pincho : listaPinchos){
            stage.addActor(pincho);
        }
        for(PinchoMovil pinchoMovil : listaPinchosMoviles){
            stage.addActor(pinchoMovil);
        }
        for(Moneda moneda : listaMonedas){
            stage.addActor(moneda);
        }

        stage.addActor(jugador);
        stage.addActor(puntos);

        gameMusic.setLooping(true);
        gameMusic.play();
    }

    @Override
    public void hide() {
        stage.clear();
        jugador.detach();
        for(Suelo suelo : listaSuelos){
            suelo.detach();
        }
        for(Pincho pincho : listaPinchos){
            pincho.detach();
        }
        for(PinchoMovil pinchoMovil : listaPinchosMoviles){
            pinchoMovil.detach();
        }
        gameMusic.stop();
        listaSuelos.clear();
        listaPinchos.clear();
        listaPinchosMoviles.clear();
        listaMonedas.clear();
        puntos.setPuntos(0);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.9f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getCamera().position.set(stage.getWidth()/3 + jugador.getX(), stage.getHeight()/2, 0);
        puntos.setPosicion(new Vector2(jugador.getX() - 70, 350));
        stage.act();
        compruebaCaida();
        nivelCompletado();
        world.step(delta, 6, 2);
        stage.draw();
        comprobarColisiones();
    }

    private void comprobarColisiones() {
        for(Moneda moneda : listaMonedas){
            if(moneda.isDisponible()){
                if(jugador.getX() + jugador.getWidth() > moneda.getX() && jugador.getX() < moneda.getX() + moneda.getWidth()){
                    puntos.setPuntos(puntos.getPuntos() + 10);
                    moneda.setDisponible(false);
                    moneda.remove();
                    sonidoMoneda.play();
                }
            }
        }
    }

    private void compruebaCaida(){
        if(jugador.getY() < 0){
            muere();
        }
    }

    private void muere() {
        jugador.setVivo(false);
        grito.play();
        guardaPuntos(puntos.getPuntos());
        stage.addAction(Actions.sequence(Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(game.gameOverScreen);
            }
        })));
    }

    private void nivelCompletado(){
        if(jugador.getX() > 6700){
            guardaPuntos(puntos.getPuntos());
            stage.addAction(Actions.sequence(Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(game.winScreen);
                }
            })));
        }
    }

    protected  void guardaPuntos(int nuevaPuntuacion) {
        ArrayList<Integer> listaPuntos = new ArrayList<Integer>();
        for(int i = 1; i <= 5; i++) {
            listaPuntos.add(game.preferences.getInteger(i + ""));
        }
        Collections.sort(listaPuntos);
        if(nuevaPuntuacion > listaPuntos.get(0)){
            listaPuntos.set(0, nuevaPuntuacion);
        }
        Collections.sort(listaPuntos);
        for(int i = 1; i <= 5; i++) {
            game.preferences.putInteger(i + "", listaPuntos.get(5-i));
        }
        game.preferences.flush();
        game.maxPuntuaciones.actualiza();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        gameMusic.dispose();
        grito.dispose();
        boing.dispose();
        sonidoMoneda.dispose();
    }
}
