import bagel.Image;
import bagel.util.Point;

/**
 * Hazard that applies damage for as long as the player is on it
 */
public class River extends Obstacle{


    private static final Image riverImage = new Image("res/river.png");;
    private final double damagePerFrame;

    /**
     * create river
     * @param position
     */
    public River(Point position) {
        super(position, riverImage);
        damagePerFrame = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("riverDamagePerFrame"));
    }

    /**
     * update interactions
     * @param player current player
     */
    public void update(Player player) {
        if (hasCollidedWith(player)) {
            if (player instanceof Robot){
                player.receiveDamage(damagePerFrame);
            }
        }
    }


}