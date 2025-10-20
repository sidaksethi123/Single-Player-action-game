import bagel.Image;
import bagel.util.Point;

/**
 * Hazard that applies damage for as long as the player is on it
 */
public class River extends Obstacle{


    private static final Image riverImage = new Image("res/river.png");;
    private final double damagePerFrame;

    public River(Point position) {
        super(position, riverImage);
        damagePerFrame = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("riverDamagePerFrame"));
    }

    public void update(Player player) {
        if (hasCollidedWith(player)) {
            if (player instanceof Robot){
                player.receiveDamage(damagePerFrame);
            }
        }
    }


}