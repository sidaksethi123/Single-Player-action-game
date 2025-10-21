import bagel.Image;
import bagel.util.Point;

/**
 * Door which can be locked or unlocked, allows the player to move to the room it's connected to
 */
public class Door extends Obstacle{
    /**
     * public attributes both for mapping layout purposes whilst in other class objects
     */
    public final String toRoomName;
    public BattleRoom battleRoom; // only set if this door is inside a Battle Room
    private boolean unlocked = false;
    private boolean justEntered = false; // when the player only just entered this door's room
    private boolean shouldLockAgain = false;

    private static final Image LOCKED = new Image("res/locked_door.png");
    private static final Image UNLOCKED = new Image("res/unlocked_door.png");

    /**
     * create door object
     * @param position coordinates of door
     * @param toRoomName where the door leads
     */
    public Door(Point position, String toRoomName) {
        super(position, LOCKED);
        this.toRoomName = toRoomName;
    }

    /**
     * Secondary construcotr
     * @param position coordinate of door
     * @param toRoomName where the door leads
     * @param battleRoom Battleroom type for this door
     */
    public Door(Point position, String toRoomName, BattleRoom battleRoom) {
        super(position, LOCKED);
        this.toRoomName = toRoomName;
        this.battleRoom = battleRoom;
    }

    /**
     * Update the state of the door such as whether locked or unlocked and consequences
     * @param player current player status
     */
    public void update(Player player) {
        if (hasCollidedWith(player)) {
            onCollideWith(player);
        } else {
            onNoLongerCollide();
        }
    }


    /**
     * open door
     * @param justEntered whether entered right now or not
     */
    public void unlock(boolean justEntered) {
        unlocked = true;
        image = UNLOCKED;
        this.justEntered = justEntered;
    }


    private void onCollideWith(Player player) {
        // when the player only just entered this door's room, overlapping with the unlocked door shouldn't trigger room transition
        if (unlocked && !justEntered) {
            ShadowDungeon.changeRoom(toRoomName);
        }
        if (!unlocked) {
            player.move(player.getPrevPosition().x, player.getPrevPosition().y);
        }
    }

    private void onNoLongerCollide() {
        // when the player only just moved away from the unlocked door after walking through it
        if (unlocked && justEntered) {
            justEntered = false;

            // Battle Room activation conditions
            if (shouldLockAgain && battleRoom != null && !battleRoom.isComplete()) {
                unlocked = false;
                image = LOCKED;
                battleRoom.activateEnemies();
            }
        }
    }

    /**
     * Lock the door
     */
    public void lock() {
        unlocked = false;
        image = LOCKED;
    }

    /**
     * check if unlocked
     * @return the status
     */
    public boolean isUnlocked() {
        return unlocked;
    }

    /**
     * if door should lock again
     */
    public void setShouldLockAgain() {
        this.shouldLockAgain = true;
    }

    /**
     * getter for door position
     * @return position of door
     */
    public Point getPosition() {
        return position;
    }
}
