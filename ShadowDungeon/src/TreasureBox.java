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
    private boolean active = true;

    public TreasureBox(Point position, double coinValue) {

        super(position, treasureImage);
        this.coinValue = coinValue;
    }

    public void update(Input input, Player player) {
        if (hasCollidedWith(player) && input.wasPressed(Keys.K)) {
            player.earnCoins(coinValue);
            active = false;
        }
    }

    public void draw() {
        image.draw(position.x, position.y);
    }

    public boolean hasCollidedWith(Player player) {
        return image.getBoundingBoxAt(position).intersects(player.getCurrImage().getBoundingBoxAt(player.getPosition()));
    }

    public boolean isActive() {
        return active;
    }
}