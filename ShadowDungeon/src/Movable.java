import bagel.util.Point;

/**
 * If something can be moved
 */
public interface Movable {

    /**
     * move the object
     * @param x new x position
     * @param y new y position
     */
    void move(double x, double y);
}
