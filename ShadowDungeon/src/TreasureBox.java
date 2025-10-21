import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

/**
 * Chest that can be unlocked by the player to earn coins
 */
public class TreasureBox extends Obstacle{

    private static final Image treasureImage = new Image("res/treasure_box.png");
    private final double coinValue;

    /**
     * create treasure object
     * @param position position of treasure
     * @param coinValue coins earned when opened
     */
    public TreasureBox(Point position, double coinValue) {

        super(position, treasureImage);
        this.coinValue = coinValue;
    }

    /**
     * update status of chest and interactions
     * @param input key event presses
     * @param player current player
     */
    public void update(Input input, Player player) {
        if (hasCollidedWith(player) && input.wasPressed(Keys.K) && (player.keyCount>=1)) {
            player.earnCoins(coinValue);
            Active = false;
            player.keyCount -= 1;
        }
    }

    /**
     * check if active
     * @return whether active
     */
    public boolean isActive() {
        return Active;
    }
}