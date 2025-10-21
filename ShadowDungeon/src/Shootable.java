/**
 * whether something can shoot an object
 */
public interface Shootable {

    /**
     * shoot the object
     * @param player current player
     */
    public void shootProjectile(Player player);
}
