import bagel.Image;
import bagel.util.Point;

/**
 * Hazard that applies damage for as long as the player is on it
 */
public class Basket extends Obstacle{
    private static final Image basketImage = new Image("res/basket.png");

    public Basket(Point position) {
        super(position, basketImage);
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