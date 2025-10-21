import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Enemy that damages player when overlapped with it
 */
public class KeyBulletKin extends Enemy implements Movable{


    private static final Image KeyBKimage= new Image("res/key_bullet_kin.png");
    private int currStatus = 0;
    private final double speed = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("keyBulletKinSpeed"));
    private ArrayList<Point> directions;

    /**
     * create Keybulletkin object
     * @param startPos initial coordinates
     * @param directions the route of the moving object
     */
    public KeyBulletKin(Point startPos, ArrayList<Point> directions) {
        super(startPos, KeyBKimage,
                Double.parseDouble(ShadowDungeon.getGameProps().getProperty("keyBulletKinHealth")), 0);
        this.directions = directions;
    }

    /**
     * Update the status and consequences of KeybulletKin
     * @param player current player status
     */
    @Override
    public void update(Player player) {

        if (hasCollidedWith(player)) {
            player.receiveDamage(0.2);
        }

         if (active){
             BattleRoomupdate();
         }
    }

    /**
     * Update how the enemy moves in BattleRooms
     */
    public void BattleRoomupdate(){
        Point goal = directions.get(currStatus);
        double distance = position.distanceTo(goal);
        double unitX = (goal.x-position.x)/ distance;
        double unitY = (goal.y-position.y)/ distance;

        double currX = position.x;
        double currY = position.y;

        //logic for when direction will be changed
        if (distance < speed){
            position = goal;
            currStatus= (currStatus + 1) % directions.size();
            return;
        }

        currX += Math.sqrt(speed) * unitX;
        currY += Math.sqrt(speed) * unitY;

        move(currX, currY);
    }


    /**
     * Move position
     * @param x x coord
     * @param y y coord
     */
    public void move(double x, double y) {
        position = new Point(x, y);

    }

}
