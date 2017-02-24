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
import com.friendlygirl.entities.FloorEntity;
import com.friendlygirl.entities.GirlEntity;
import com.friendlygirl.entities.SpikeEntity;

import java.util.ArrayList;

public class GameScreen extends BaseScreen {

    private Stage stage;
    private World world;
    private GirlEntity girl;
    private ArrayList<SpikeEntity> spikeList = new ArrayList<SpikeEntity>();
    private ArrayList<FloorEntity> floorList = new ArrayList<FloorEntity>();
    private Music gameMusic;
    private Sound grito, boing;

    public GameScreen(final MainGame game) {
        super(game);
        gameMusic = game.getManager().get("audio/gamemusic.ogg");
        grito = game.getManager().get("audio/grito.ogg");
        boing = game.getManager().get("audio/boing.ogg");
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
                        girl.setAlive(false);
                        grito.play();
                        stage.addAction(Actions.sequence(Actions.delay(1.5f), Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                game.setScreen(game.gameOverScreen);
                            }
                        })));
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
                boing.play();
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
        //stage.setDebugAll(true);
        Texture girlTexture = game.getManager().get("girl.png");
        TextureRegion girlRegion = new TextureRegion(girlTexture, 0, 0, 140, 185);
        Texture floorTexture = game.getManager().get("floor.png");
        Texture overfloorTexture = game.getManager().get("overfloor.png");
        Texture spikeTexture = game.getManager().get("spike.png");
        girl = new GirlEntity(world, girlRegion, new Vector2(2, 1.5f));
        floorList.add(new FloorEntity(world, floorTexture, overfloorTexture, -10, 1000, 1));
        spikeList.add(new SpikeEntity(world, spikeTexture, 6, 1));

        stage.addActor(girl);

        for(FloorEntity floorEntity : floorList){
            stage.addActor(floorEntity);
        }
        for(SpikeEntity spikeEntity : spikeList){
            stage.addActor(spikeEntity);
        }

        gameMusic.setVolume(0.75f);
        gameMusic.play();
    }

    @Override
    public void hide() {
        girl.detach();
        girl.remove();
        for(FloorEntity floorEntity : floorList){
            floorEntity.detach();
            floorEntity.remove();
        }
        for(SpikeEntity spikeEntity : spikeList){
            spikeEntity.detach();
            spikeEntity.remove();
        }
        gameMusic.stop();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getCamera().position.set(stage.getWidth()/3 + girl.getX(), stage.getHeight()/2, 0);
        stage.act();
        world.step(delta, 6, 2);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        gameMusic.dispose();
    }
}
