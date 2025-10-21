import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

/**
 * Area in Prep or End Room where the player can trigger a game reset
 */
public class RestartArea {
    private final Point position;
    private final Image image;

    /**
     * create the area
     * @param position position of the area
     */
    public RestartArea(Point position) {
        this.position = position;
        this.image = new Image("res/restart_area.png");
    }

    /**
     * update for when player interacts with area
     * @param input key event press
     * @param player current player object
     */
    public void update(Input input, Player player) {
        if (hasCollidedWith(player) && input.wasPressed(Keys.ENTER)) {
            ShadowDungeon.resetGameState(ShadowDungeon.getGameProps());
        }
    }

    /**
     * draw the area
     */
    public void draw() {
        image.draw(position.x, position.y);
    }

    /**
     * check if collided with player
     * @param player current player
     * @return return whether collision occured
     */
    public boolean hasCollidedWith(Player player) {
        return image.getBoundingBoxAt(position).intersects(player.getCurrImage().getBoundingBoxAt(player.getPosition()));
    }
}
