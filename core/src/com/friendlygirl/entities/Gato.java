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

public class Gato extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private boolean vivo = true, saltando = false;

    public Gato(World world, Texture texture, Vector2 posicion) {
        this.texture = texture;
        this.world = world;

        BodyDef def = new BodyDef();
        def.position.set(posicion);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);
        fixture = body.createFixture(box, 3);
        fixture.setUserData("gato");
        box.dispose();

        setSize(0.5f * PIXELS_EN_METROS, 0.5f * PIXELS_EN_METROS);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.5f) * PIXELS_EN_METROS, (body.getPosition().y - 0.5f) * PIXELS_EN_METROS);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        body.applyForceToCenter(0, 30, true);
    }

    public void saltar() {
        if(!saltando && vivo){
            saltando = true;
            Vector2 position = body.getPosition();
            body.applyLinearImpulse(0, 15, position.x, position.y, true);
        }
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public boolean isSaltando() {
        return saltando;
    }

    public void setSaltando(boolean saltando) {
        this.saltando = saltando;
    }
}
