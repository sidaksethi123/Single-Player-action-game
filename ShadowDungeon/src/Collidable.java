/**
 * If something is collidable with player
 */
public interface Collidable {

    /**
     * method for checking if player has been collided with
     * @param player current player
     * @return whether player has been collided with
     */
    public boolean hasCollidedWith(Player player);

}
