import bagel.Image;
import bagel.util.Point;

/**
 * Obstacle that blocks player
 */
public class Table extends Obstacle{
    private static final Image tableImage = new Image("res/table.png");

    /**
     * create table
     * @param position current position
     */
    public Table(Point position) {
        super(position, tableImage);
    }


    /**
     * check if active
     * @return whether active
     */
    public boolean isActive(){
        return Active;
    }
}