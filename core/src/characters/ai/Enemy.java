package characters.ai;

import characters.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import sprites.Systems;

public class Enemy extends AiCharacter {

    public Systems targetSystem;
    public Systems currentContactSystem; // used for contact listener
    public String mode;
    public Ability ability;
    public static int numberofInfiltrators;
    public boolean usingAbility; 
    /**
     * Enemy.

     * @param world The game world
     *
     * @param x position x
     *
     * @param y position y
     */
    public Enemy(World world, float x, float y) {
        super(world, x, y);
        this.destX = x;
        this.destY = y;
        numberofInfiltrators++;
        this.b2body.setUserData("Infiltrators" + numberofInfiltrators);
        ability = new Ability();
        System.out.println("Enemy get ability:" + ability.randomIndex);
        createEdgeShape(ability);
        mode = "";
        usingAbility = false;
    }

    /**
     * Create an EdgeShape for enemy to sensor auber for special ability
     *
     */
    public void createEdgeShape(Ability ability) {

        EdgeShape sensoringArea = new EdgeShape();
        sensoringArea.set(new Vector2(64, 32), new Vector2(64,-32));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = sensoringArea;
        fixtureDef.isSensor = true;
        // store ability in sensor userdata to retrieve it in contactListener
        b2body.createFixture(fixtureDef).setUserData(ability);

    }

    @Override
    public void update(float delta) {
        super.update(delta);

        ability.update(delta, this);
        if (!ability.inUse) {
            usingAbility = false;
        }
    }

    /**
     * set sabotage system target.
     *
     * @param system Systems Arraylist
     */
    public void set_target_system(Systems system) {
        targetSystem = system;
    }

    /**
     * Get target system object.
     *
     * @return targeted system
     */
    public Systems get_target_system() {
        return targetSystem;
    }

    /**
     * ability to sabotage the system.
     *
     * @param system system object
     */
    public void sabotage(Systems system) {
        if (system.hp > 0) {
            system.hp -= 1;
        } else {
            system.set_sabotaged();
        }
    }

    /**
     * set enemy to attcking mode.
     */
    public void set_attackSystemMode() {
        mode = "attacking_system";
    }

    /**
     * set enemy to standby mode.
     */
    public void set_standByMode() {
        mode = "";
    }

    /**
     * check enemy is attcking a system or not.
     *
     * @return true if it is in attacking mode
     */
    public boolean is_attcking_mode() {
        return mode.equals("attacking_system");
    }

    /**
     * check enemy is standby or not.
     *
     * @return true if it is in standby mode
     */
    public boolean is_standBy_mode() {
        return mode.equals("");
    }

    /**
     * set enemy to arrested.
     */
    public void set_ArrestedMode() {
        mode = "arrested";
    }

    public boolean isArrested() {
        return mode.equals("arrested");
    }


    // TO DO
    // Enemies special abilities
    // ...




}
