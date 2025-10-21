/**
 * whether something can be damaged
 */
public interface Damageable {
    /**
     * apply damage
     * @param damage how much damage
     */
    public void receiveDamage(double damage);

    /**
     * get heath
     * @return health
     */
    public double getHealth();
}
