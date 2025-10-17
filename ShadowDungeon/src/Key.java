import bagel.Image;
import bagel.util.Point;

/**
 * Hazard that applies damage for as long as the player is on it
 */
public class Key extends Obstacle{
    private static final Image tableImage = new Image("res/key.png");

    public Key(Point position) {
        super(position, tableImage);
    }

    @Override
    public void update(Player player) {
        System.out.println("hi");
    }

    public void draw() {
        image.draw(position.x, position.y);
    }

    public boolean hasCollidedWith(Player player) {
        return image.getBoundingBoxAt(position).intersects(player.getCurrImage().getBoundingBoxAt(player.getPosition()));
    }
}