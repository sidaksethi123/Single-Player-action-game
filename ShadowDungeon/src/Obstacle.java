import bagel.Image;
import bagel.util.Point;


public class Obstacle {

    public final Point position;
    public final Image image;
    private static final Image fireImage = new Image("res/fireball.png");
    private static final Image bulletImage = new Image("res/bullet.png");
    public boolean Active = true;

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

    public boolean bulletcollision(Projectile bullet){
        return image.getBoundingBoxAt(position).intersects
                (bulletImage.getBoundingBoxAt(bullet.position));
    }

    public boolean fireballcollision(Projectile bullet){
        return image.getBoundingBoxAt(position).intersects
                (fireImage.getBoundingBoxAt(bullet.position));
    }
}
