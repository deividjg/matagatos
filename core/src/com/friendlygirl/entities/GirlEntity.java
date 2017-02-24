package com.friendlygirl.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.friendlygirl.Constants.PIXELS_IN_METER;

public class GirlEntity extends Actor {

    private TextureRegion textureRegion;
    private World world;
    private Body body;
    private Fixture fixture;
    private boolean alive = true, jumping = false;

    public GirlEntity(World world, TextureRegion textureRegion, Vector2 position) {
        this.textureRegion = textureRegion;
        this.world = world;

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);
        fixture = body.createFixture(box, 3);
        fixture.setUserData("girl");
        box.dispose();

        setSize(PIXELS_IN_METER, PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.5f) * PIXELS_IN_METER, (body.getPosition().y - 0.5f) * PIXELS_IN_METER);
        batch.draw(textureRegion, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        if(Gdx.input.justTouched()){
            jump();
        }

        if(Gdx.input.getAccelerometerX()>0){
            Vector2 position = body.getPosition();
            body.applyForce(2, 0, position.x, position.y, true);
        }

        if(Gdx.input.getAccelerometerX()<0){
            Vector2 position = body.getPosition();
            body.applyForce(-2, 0, position.x, position.y, true);
        }
    }

    public void jump() {
        if(!jumping){
            jumping = true;
            Vector2 position = body.getPosition();
            body.applyLinearImpulse(0, 15, position.x, position.y, true);
        }
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }
}
