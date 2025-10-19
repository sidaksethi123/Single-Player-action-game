import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Enemy that gets removed when the player overlaps with it
 */
public class KeyBulletKin extends Enemy{


    private static final Image KeyBKimage= new Image("res/key_bullet_kin.png");
    ArrayList<Point> directions;



    private int currStatus = 0;
    private double speed = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("keyBulletKinSpeed"));


    public KeyBulletKin(Point startPos, ArrayList<Point> directions) {
        super(startPos, KeyBKimage,
                Double.parseDouble(ShadowDungeon.getGameProps().getProperty("keyBulletKinHealth")), 0);
        this.directions = directions;
    }

    @Override
    public void update(Player player) {

        if (hasCollidedWith(player)) {
            dead = true;
            active = false;
        }

         if (!active){
             BattleRoomupdate();
         }
    }

    public void BattleRoomupdate(){
        Point goal = 
        double initDistance = position.distanceTo(directions.get(currStatus));
        double unitX = (directions.get(currStatus).x-position.x)/ initDistance;
        double unitY = (directions.get(currStatus).y-position.y)/ initDistance;

        double currX = position.x;
        double currY = position.y;

        currX += Math.sqrt(speed) * unitX;
        currY += Math.sqrt(speed) * unitY;

        move(currX, currY);
        if (currStatus == 4){
            currStatus = 0;
        }
        else{
            currStatus += 1;
        }
    }



    public void move(double x, double y) {
        position = new Point(x, y);

    }

}
