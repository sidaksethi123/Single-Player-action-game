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
        if (hasCollidedWith(player)) {
            // set the player to its position prior to attempting to move through this wall
            player.move(player.getPrevPosition().x, player.getPrevPosition().y);
        }

    }

    public boolean hasCollidedWith(Player player) {
        return image.getBoundingBoxAt(position).intersects(player.getCurrImage().getBoundingBoxAt(player.getPosition()));
    }

    public void draw() {
        image.draw(position.x, position.y);
    }
}
