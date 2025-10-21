import bagel.Image;
import bagel.util.Point;
import bagel.*;

/**
 * Bullet object shot by player's weapon
 */
public class Bullet extends Projectile {


    private static final Image bulletImage = new Image("res/bullet.png");

    /**
     * create bullet object
     * @param damage bullet damage
     * @param position starting position
     * @param direction where bullet will go
     */
    public Bullet(double damage, Point position, Point direction){
        super(damage,position,direction,bulletImage,
                Double.parseDouble(ShadowDungeon.getGameProps().getProperty("bulletSpeed")));
        double initDistance = position.distanceTo(direction);
    }





}
