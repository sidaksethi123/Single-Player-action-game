import bagel.Image;
import bagel.util.Point;

/**
 * Hazard that provides cash upon breaking
 */
public class Basket extends Obstacle{
    private static final Image basketImage = new Image("res/basket.png");

    /**
     * Create obstacle object
     * @param position obstacle position
     */
    public Basket(Point position) {
        super(position, basketImage);
    }

    /**
     * Track state
     * @return provides the state of object
     */
    public boolean isActive(){
        return Active;
    }
}