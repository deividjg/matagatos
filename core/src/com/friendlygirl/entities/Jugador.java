package com.friendlygirl.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.friendlygirl.GameScreen;

import static com.friendlygirl.Constantes.PIXELS_EN_METROS;

public class Jugador extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private boolean vivo, saltando = false, acudePumba =false;

    public Jugador(World world, Texture texture, Vector2 posicion) {
        this.texture = texture;
        this.world = world;

        BodyDef def = new BodyDef();
        def.position.set(posicion);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);
        fixture = body.createFixture(box, 3);
        fixture.setUserData("jugador");
        box.dispose();
        vivo = true;

        setSize(PIXELS_EN_METROS, PIXELS_EN_METROS);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.5f) * PIXELS_EN_METROS, (body.getPosition().y - 0.5f) * PIXELS_EN_METROS);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        if(Gdx.input.getAccelerometerY()>0.5 && vivo && body.getPosition().x < 5 || Gdx.input.isKeyPressed(Keys.D) && vivo && body.getPosition().x < 5){
            body.applyForceToCenter(10, 0, true);
        }

        if(Gdx.input.getAccelerometerY()<-0.5 && vivo && body.getPosition().x > 1 || Gdx.input.isKeyPressed(Keys.A) && vivo && body.getPosition().x > 1 ){
            body.applyForceToCenter(-10, 0, true);
        }
        if (Gdx.input.justTouched() && acudePumba){
            saltar();
        }
    }

    public void saltar() {
        if(!saltando && vivo){
            saltando = true;
            Vector2 position = body.getPosition();
            body.applyLinearImpulse(0, 20, position.x, position.y, true);
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

    public boolean isAcudePumba() {
        return acudePumba;
    }

    public void setAcudePumba(boolean acudePumba) {
        this.acudePumba = acudePumba;
    }
}
