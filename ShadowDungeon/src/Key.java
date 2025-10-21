import bagel.Image;
import bagel.util.Point;

/**
 * Object dropped by Keybulletkin used to open treasure
 */
public class Key extends Obstacle{
    private static final Image keyImage = new Image("res/key.png");

    /**
     * create key
     * @param position position where it is dropped
     */
    public Key(Point position) {
        super(position, keyImage);
    }

    /**
     * update status of key
     * @param player current player
     */
    public void update(Player player) {
        if (hasCollidedWith(player)) {
            // set the player to its position prior to attempting to move through this wall
            Active = false;
            player.keyCount += 1;
        }

    }

    /**
     * check if active
     * @return whether active
     */
    public boolean isActive(){
        return Active;
    }
}