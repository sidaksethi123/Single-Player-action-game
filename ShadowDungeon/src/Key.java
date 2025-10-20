import bagel.Image;
import bagel.util.Point;

/**
 * Hazard that applies damage for as long as the player is on it
 */
public class Key extends Obstacle{
    private static final Image keyImage = new Image("res/key.png");
    public boolean Active = true;

    public Key(Point position) {
        super(position, keyImage);
    }
    public void update(Player player) {
        if (hasCollidedWith(player)) {
            // set the player to its position prior to attempting to move through this wall
            Active = false;
            player.keyCount += 1;
        }

    }

    public boolean isActive(){
        return Active;
    }
}