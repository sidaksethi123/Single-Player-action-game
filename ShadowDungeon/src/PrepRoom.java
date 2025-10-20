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
    private static Point robotPos;
    private static Point marinePos;
    Font prompt = new Font("res/wheaton.otf", 24);

    public void initEntities(Properties gameProperties) {
        // find the configuration of game objects for this room
        robotPos = IOUtils.parseCoords(gameProperties.getProperty("Robot"));
        marinePos = IOUtils.parseCoords(gameProperties.getProperty("Marine"));

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


    public void update(Input input) {
        UserInterface.drawStartMessages();

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


        ROBOT_IMAGE.draw(robotPos.x, robotPos.y);
        MARINE_IMAGE.draw(marinePos.x, marinePos.y);

        prompt.drawString("Marine: No injury in rivers", 30, robotPos.y);
        prompt.drawString("Robot: +5$ per kill", 685, robotPos.y);

        // door unlock mechanism
        if ((input.wasPressed(Keys.R) || input.wasPressed(Keys.M))) {

            Point TempPosition = player.position;
            if(input.wasPressed(Keys.R)){
                player = new Robot(TempPosition);
            }
            else{
                player = new Marine(TempPosition);
            }
            ShadowDungeon.setplayertype(player);

            if (!findDoor().isUnlocked()) {
                findDoor().unlock(false);
            }
        }

    }


    public void setPlayer(Player player) {
        this.player = player;
    }


    public Door findDoor() {
        return door;
    }

    public Door findDoorByDestination() {
        return door;
    }
}
