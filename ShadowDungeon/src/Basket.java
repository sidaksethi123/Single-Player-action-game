import bagel.Image;
import bagel.util.Point;

/**
 * Hazard that applies damage for as long as the player is on it
 */
public class Basket extends Obstacle{
    private static final Image basketImage = new Image("res/basket.png");
    public boolean Active = true;

    public Basket(Point position) {
        super(position, basketImage);
    }


    public boolean isActive(){
        return Active;
    }
}