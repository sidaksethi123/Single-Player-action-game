import bagel.Input;
import bagel.Keys;
import bagel.Image;
import bagel.util.Point;
import bagel.Font;
import java.util.Map;
import java.util.Properties;
import bagel.Window;
/**
 * Room where the game starts
 */
public class PrepRoom extends Room{
    private Player player;
    private Door door;
    private RestartArea restartArea;

    private static final Image ROBOT_IMAGE = new Image("res/robot_sprite.png");
    private static final Image MARINE_IMAGE = new Image("res/marine_sprite.png");
    // not final, for future proofing purposes
    private static Point robotPos;
    private static Point marinePos;
    private static Point robotMsg;
    private static Point marineMsg;

    /**
     * dynamically intialise fields
     * @param gameProperties game properties
     */
    public void initEntities(Properties gameProperties) {
        // find the configuration of game objects for this room
        robotPos = IOUtils.parseCoords(gameProperties.getProperty("Robot"));
        marinePos = IOUtils.parseCoords(gameProperties.getProperty("Marine"));
        robotMsg = IOUtils.parseCoords(gameProperties.getProperty("robotMessage"));
        marineMsg = IOUtils.parseCoords(gameProperties.getProperty("marineMessage"));

        for (Map.Entry<Object, Object> entry: gameProperties.entrySet()) {
            String roomSuffix = String.format(".%s", ShadowDungeon.PREP_ROOM_NAME);
            if (entry.getKey().toString().contains(roomSuffix)) {
                String objectType = entry.getKey().toString().substring(0, entry.getKey().toString().length() - roomSuffix.length());
                String propertyValue = entry.getValue().toString();

                switch (objectType) {
                    case "door":
                        String[] coordinates = propertyValue.split(",");
                        door = new Door(IOUtils.parseCoords(propertyValue), coordinates[2]);
                        break;
                    case "restartarea":
                        restartArea = new RestartArea(IOUtils.parseCoords(propertyValue));
                        break;
                    default:
                }
            }
        }
    }

    /**
     * update the objects in the room
     * @param input key event presses
     */
    public void update(Input input) {
        UserInterface.drawStartMessages();
        ROBOT_IMAGE.draw(robotPos.x, robotPos.y);
        MARINE_IMAGE.draw(marinePos.x, marinePos.y);

        UserInterface.drawData("Marine: No injury in rivers", 20, marineMsg);
        UserInterface.drawData("Robot: +5$ per kill", 20, robotMsg);

        UserInterface.drawTextCentered("selectMessage", 24, 509);
        // update and draw all game objects in this room
        door.update(player);
        door.draw();
        if (stopUpdatingEarlyIfNeeded()) {
            return;
        }

        restartArea.update(input, player);
        restartArea.draw();


        if (player != null) {
            player.update(input);
            player.draw();
        }

        // door unlock mechanism
        if ((input.wasPressed(Keys.R) || input.wasPressed(Keys.M))) {

            if(input.wasPressed(Keys.R)){
                player = new Robot(player);
            }
            else{
                player = new Marine(player);
            }
            ShadowDungeon.setplayertype(player);

            if (!findDoor().isUnlocked()) {
                findDoor().unlock(false);
            }
        }

    }

    /**
     * set player status
     * @param player current player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * find door
     * @return door object
     */
    public Door findDoor() {
        return door;
    }

    /**
     * find door by destination
     * @return door object
     */
    public Door findDoorByDestination() {
        return door;
    }
}
