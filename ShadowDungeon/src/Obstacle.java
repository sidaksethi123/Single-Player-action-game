import bagel.Image;
import bagel.util.Point;


public class Obstacle {
    public final Point position;
    public final Image image;


    public Obstacle(Point position, Image image) {
        this.position = position;
        this.image = image;

    }

    public void update(Player player) {
    }


}
