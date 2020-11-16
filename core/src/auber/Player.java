package auber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Main player object for the game.
 */
public class Player extends Sprite {
    public World world;
    public Body b2body;
    public float health;
    public boolean ishealing;
    private float playerSpeed = 60f;

    /**
     * creates an semi-initalised player the physics body is still uninitiated.

     * @param world The game world
     * 
     * @param name The name of the sprite
     * 
     * @param x The inital x location of the player
     * 
     * @param y The inital y location of the player
     */
    public Player(World world, String name, float x, float y)  {
        super(new Texture(name));
        this.world = world;
        this.health = 10f;
        this.ishealing = false;
        setPosition(x, y);
        createBody();
    }
        
    /**
    * Creates the physics bodies for the player Sprite.
    */
    public void createBody()  {
        BodyDef bdef = new BodyDef();
        bdef.position.set(this.getX() + getWidth() + 4, this.getY() + getHeight() + 4);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2, getHeight() / 2);

        fdef.shape = shape;

        b2body.setLinearDamping(20f);

        b2body.createFixture(fdef).setUserData("auber"); // for contact listener
        b2body.setUserData("auber");
        shape.dispose();

    }
    

    /**
     * Updates the player, should be called every update cycle.

     * @param delta The time in secconds since the last update
     */
    public void updatePlayer(float delta)  {

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            b2body.applyLinearImpulse(new Vector2(-playerSpeed, 0),
                b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            b2body.applyLinearImpulse(new Vector2(playerSpeed, 0),
                b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            b2body.applyLinearImpulse(new Vector2(0, playerSpeed),
                b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            b2body.applyLinearImpulse(new Vector2(0, -playerSpeed),
                b2body.getWorldCenter(), true);
        }

        // position sprite properly within the box
        this.setPosition(b2body.getPosition().x - getWidth() / 2,
                         b2body.getPosition().y - getHeight() / 2);

        // should be called each loop of rendering
        healing(delta);
    }

    /**
     *
     * @param isheal set ishealing to true or false
     */
    public void setHealing(boolean isheal){

        ishealing = isheal;

    }


    /**
     * Healing auber
     */
    public void healing(float delta){
        // healing should end or not start if auber left healing pod or not contact with healing pod
        if (b2body.getUserData() == "auber"){
            setHealing(false);
            return;
        }
        // healing should start if auber in healing pod and not in full health
        if(b2body.getUserData() == "ready_to_heal" && health < 100f){
            setHealing(true);
        }
        // healing shouln't start if auber is in full health and healing should end if auber being healed to full health
        else if (b2body.getUserData() == "ready_to_heal" && health == 100f){
            setHealing(false);
            // test purpose, delet when deploy
            System.out.println("Auber is in full health, no need for healing ");
        }
        // healing process
        if(ishealing){
            // adjust healing amount accrodingly
            health += 20f * delta;
            if (health > 100f) {
                health = 100f;
            }
            // test prupose, delet when depoly
            System.out.println("Auber is healing, Auber current health: " + health);
        }

    }

}
