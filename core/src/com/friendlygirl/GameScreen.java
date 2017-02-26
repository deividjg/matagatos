package com.friendlygirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.friendlygirl.entities.FloorEntity;
import com.friendlygirl.entities.GirlEntity;
import com.friendlygirl.entities.Moneda;
import com.friendlygirl.entities.PinchoMovil;
import com.friendlygirl.entities.Puntos;
import com.friendlygirl.entities.SpikeEntity;

import java.util.ArrayList;
import java.util.Collections;

public class GameScreen extends BaseScreen {

    private Stage stage;
    private World world;
    private Puntos puntos;
    private GirlEntity girl;
    private ArrayList<SpikeEntity> spikeList = new ArrayList<SpikeEntity>();
    private ArrayList<FloorEntity> floorList = new ArrayList<FloorEntity>();
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
                if(areCollided(contact, "girl", "floor")){
                    girl.setJumping(false);
                }

                if(areCollided(contact, "girl", "spike")){
                    if(girl.isAlive()){
                        muere();
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
                if(areCollided(contact, "girl", "floor")) {
                    if (girl.isAlive()) {
                        if(girl.isJumping()) {
                            boing.play();
                        }
                    }
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    @Override
    public void show() {
        Texture girlTexture = game.getManager().get("girl.png");
        TextureRegion girlRegion = new TextureRegion(girlTexture, 0, 0, 140, 185);
        Texture floorTexture = game.getManager().get("floor.png");
        Texture overfloorTexture = game.getManager().get("overfloor.png");
        Texture spikeTexture = game.getManager().get("spikeblood.png");
        Texture monedaTexture = game.getManager().get("moneda.png");
        TextureRegion monedaRegion = new TextureRegion(monedaTexture, 0, 0, 290, 348);
        Texture pinchoMovilTexture = game.getManager().get("spikebloodinverse.png");

        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture, -10, 28, 1));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture, 8, 6, 2));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture, 20, 20, 1));
        spikeList.add(new SpikeEntity(world, spikeTexture, 6, 1));
        spikeList.add(new SpikeEntity(world, spikeTexture, 11, 2));
        listaPinchosMoviles.add(new PinchoMovil(world, pinchoMovilTexture, 4, 1));
        listaMonedas.add(new Moneda(monedaRegion, new Vector2(5.9f, 2.5f)));
        listaMonedas.add(new Moneda(monedaRegion, new Vector2(10.9f, 3.5f)));
        listaMonedas.add(new Moneda(monedaRegion, new Vector2(18.5f, 3)));
        listaMonedas.add(new Moneda(monedaRegion, new Vector2(19.5f, 3)));
        girl = new GirlEntity(world, girlRegion, new Vector2(2, 1.5f));

        for(FloorEntity floorEntity : floorList){
            stage.addActor(floorEntity);
        }
        for(SpikeEntity spikeEntity : spikeList){
            stage.addActor(spikeEntity);
        }
        for(PinchoMovil pinchoMovil : listaPinchosMoviles){
            stage.addActor(pinchoMovil);
        }
        for(Moneda moneda : listaMonedas){
            stage.addActor(moneda);
        }

        stage.addActor(girl);

        stage.addActor(puntos);

        gameMusic.setLooping(true);
        gameMusic.play();
    }

    @Override
    public void hide() {
        stage.clear();

        girl.detach();

        for(FloorEntity floorEntity : floorList){
            floorEntity.detach();
        }
        for(SpikeEntity spikeEntity : spikeList){
            spikeEntity.detach();
        }
        for(PinchoMovil pinchoMovil : listaPinchosMoviles){
            pinchoMovil.detach();
        }
        gameMusic.stop();
        floorList.clear();
        spikeList.clear();
        listaPinchosMoviles.clear();
        listaMonedas.clear();
        puntos.setPuntos(0);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.9f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getCamera().position.set(stage.getWidth()/3 + girl.getX(), stage.getHeight()/2, 0);
        puntos.setPosicion(new Vector2(girl.getX() - 70, 350));
        stage.act();
        compruebaCaida();
        world.step(delta, 6, 2);
        stage.draw();
        comprobarColisiones();
    }

    private void comprobarColisiones() {
        for(Moneda moneda : listaMonedas){
            if(moneda.isDisponible()){
                if(girl.getX() + girl.getWidth() > moneda.getX() && girl.getX() < moneda.getX() + moneda.getWidth()){
                    puntos.setPuntos(puntos.getPuntos() + 10);
                    moneda.setDisponible(false);
                    moneda.remove();
                    sonidoMoneda.play();
                }
            }
        }
    }

    private void compruebaCaida(){
        if(girl.getY() < 0){
            muere();
        }
    }

    private void muere() {
        girl.setAlive(false);
        grito.play();
        guardaPuntos(puntos.getPuntos());

        stage.addAction(Actions.sequence(Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(game.gameOverScreen);
            }
        })));
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
