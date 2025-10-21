import bagel.Image;
import bagel.util.Point;
import bagel.*;

/**
 * Projectile shot by Enemies
 */
public class Fireball extends Projectile{

    private static final Image fireImage = new Image("res/fireball.png");

    /**
     * create fireball
     * @param position initial position
     * @param direction where it will go
     */
    public Fireball(Point position, Point direction){
        super(Double.parseDouble(ShadowDungeon.getGameProps().getProperty("fireballDamage")), position, direction,
        fireImage, Double.parseDouble(ShadowDungeon.getGameProps().getProperty("fireballSpeed")));
        double initDistance = position.distanceTo(direction);
    }

    /**
     * update fireball state
     * @param player current player
     */
    public void update(Player player){
        if (playercollision(player)){
            if (isActive) {
                player.receiveDamage(40);
                isActive = false;
            }
        }
        projection();

    }

    /**
     * Player collision
     * @param player current player
     * @return whether collided or not
     */
    public boolean playercollision(Player player){
        return fireImage.getBoundingBoxAt(position).intersects
                (player.currImage.getBoundingBoxAt(player.position));
    }


}
