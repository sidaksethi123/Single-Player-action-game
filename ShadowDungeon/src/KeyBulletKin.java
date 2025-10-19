import bagel.Image;
import bagel.util.Point;

/**
 * Enemy that gets removed when the player overlaps with it
 */
public class KeyBulletKin extends Enemy{


    private static final Image KeyBKimage= new Image("res/key_bullet_kin.png");

    private static final Point[] directionsBroomA = new Point[5];
    private static final Point[] directionsBroomB = new Point[3];
    private final char CurrBattleRoom;


    private int currStatus = 0;
    private double speed = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("keyBulletKinSpeed"));


    public KeyBulletKin(Point startPos, char Room) {
        super(startPos, KeyBKimage);
        this.CurrBattleRoom = Room;
    }

    @Override
    public void update(Player player) {

        if (hasCollidedWith(player)) {
            dead = true;
            active = false;
        }

         if (!active){
            if (CurrBattleRoom == 'A'){
                BattleRoomAupdate();
            }
            else{
                BattleRoomBupdate();
            }

         }
    }

    public void BattleRoomAupdate(){
        double initDistance = position.distanceTo(directionsBroomA[currStatus]);
        double unitX = (directionsBroomA[currStatus].x-position.x)*(1/ initDistance);
        double unitY = (directionsBroomA[currStatus].y-position.y)*(1/ initDistance);

        double currX = position.x;
        double currY = position.y;

        currX = Math.sqrt(speed) * unitX;
        currY = Math.sqrt(speed) * unitY;

        move(currX, currY);
        if (currStatus == 4){
            currStatus = 0;
        }
        else{
            currStatus += 1;
        }
    }

    public void BattleRoomBupdate(){
        double initDistance = position.distanceTo(directionsBroomB[currStatus]);
        double unitX = (directionsBroomB[currStatus].x-position.x)*(1/ initDistance);
        double unitY = (directionsBroomA[currStatus].y-position.y)*(1/ initDistance);

        double currX = position.x;
        double currY = position.y;

        currX = Math.sqrt(speed) * unitX;
        currY = Math.sqrt(speed) * unitY;

        move(currX, currY);
        if (currStatus == 2){
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
