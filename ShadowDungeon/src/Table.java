import bagel.Image;
import bagel.util.Point;

/**
 * Hazard that applies damage for as long as the player is on it
 */
public class Table extends Obstacle{
    private static final Image tableImage = new Image("res/table.png");
    private boolean Active = true;

    public Table(Point position) {
        super(position, tableImage);
    }



    public boolean isActive(){
        return Active;
    }
}