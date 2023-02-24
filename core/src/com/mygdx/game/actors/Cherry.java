package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Cherry extends Actor {

    private static float CHERRY_WIDTH = 0.4f;
    private static float CHERRY_HEIGHT = 0.4f;

    //Declaramos los estados posibles del personaje, que sera, vivo o muerto
    private static final int STATE_NORMAL = 0;
    private static final int STATE_DEAD = 1;

    private boolean eliminado;

    //Declaramos los atributos del PacMan
    //private Animation<TextureRegion> pacManAnimation;
    private TextureRegion texture;
    private Vector2 position;

    //Creamos el mundo
    private World world;

    //Creamos el cuerpo de la cereza y la fisica
    private Body body;
    private Fixture fixture;

    public Cherry(World world, TextureRegion texture, Vector2 position, String usuario) {

        this.texture = texture;
        this.position = position;
        this.world = world;
        this.eliminado = false;

        createBody();
        createFixture(usuario);
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Body getBody() {
        return body;
    }

    //Metodo para crear el cuerpo
    private void createBody() {
        //Creamos el body
        BodyDef bodyDef = new BodyDef();
        //Le establecemos la posicion, la cual sera la misma de la del PacMan,
        // ya que la cojemos del constructor
        bodyDef.position.set(this.position);
        //Le asignamos un tipo
        bodyDef.type = BodyDef.BodyType.StaticBody;
        //Lo creamos
        this.body = this.world.createBody(bodyDef);
    }

    //Metodo para crear la fisica
    private void createFixture(String usuario) {
        //Como es un circulo, le damos un radio que sera el tamaño de la fisica
        PolygonShape poligon = new PolygonShape();
        poligon.setAsBox(CHERRY_WIDTH/2, CHERRY_HEIGHT/2);

        //Creamos la fisica
        this.fixture = this.body.createFixture(poligon, 8);
        this.fixture.setSensor(true);
        //Le asignamos a esta fisica un "nombre" para identificarlo
        this.fixture.setUserData(usuario);

        poligon.dispose();
    }

    @Override
    public void act(float delta) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //Establecemos la posicion de la fisica restandole la mitad del tamaño del dibujo del PacMan
        setPosition(body.getPosition().x - (CHERRY_WIDTH/2), body.getPosition().y - (CHERRY_HEIGHT/2));
        //Dibujamos la cereza
        batch.draw(texture, getX(), getY(), CHERRY_WIDTH, CHERRY_HEIGHT);
    }

    public void detach() {
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
        this.eliminado = true;
    }


}