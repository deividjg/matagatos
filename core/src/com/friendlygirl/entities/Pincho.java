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

import static com.friendlygirl.Constantes.PIXELS_EN_METROS;

public class Pincho extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;

    public Pincho(World world, Texture texture, float x, float y) {
        this.world = world;
        this.texture = texture;

        BodyDef def = new BodyDef();
        def.position.set(x, y + 0.25f);
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(-0.25f, -0.25f);
        vertices[1] = new Vector2(0.25f, -0.25f);
        vertices[2] = new Vector2(0, 0.25f);
        box.set(vertices);
        fixture = body.createFixture(box, 1);
        fixture.setUserData("pincho");
        box.dispose();

        setPosition((x - 0.25f) * PIXELS_EN_METROS, y * PIXELS_EN_METROS);
        setSize(0.5f * PIXELS_EN_METROS, 0.5f * PIXELS_EN_METROS);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
