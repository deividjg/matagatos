package com.friendlygirl.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.friendlygirl.Constants.PIXELS_IN_METER;

public class PinchoMovil extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;

    public PinchoMovil(World world, Texture texture, float x, float y) {
        this.texture = texture;
        this.world = world;

        BodyDef def = new BodyDef();
        def.position.set(x, y + 0.25f);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(-0.25f, -0.25f);
        vertices[1] = new Vector2(0.25f, -0.25f);
        vertices[2] = new Vector2(0, 0.25f);
        box.set(vertices);
        fixture = body.createFixture(box, 3);
        fixture.setUserData("spike");
        box.dispose();

        setPosition((x - 0.25f) * PIXELS_IN_METER, y * PIXELS_IN_METER);
        setSize(0.5f * PIXELS_IN_METER, 0.5f * PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.25f) * PIXELS_IN_METER, (body.getPosition().y -0.25f) * PIXELS_IN_METER);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        if(body.getPosition().y < 1.3){
            body.setLinearVelocity(0, 4.8f);
        }else if(body.getPosition().y > 1.3){
            body.applyForceToCenter(0, 2, true);
        }
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
