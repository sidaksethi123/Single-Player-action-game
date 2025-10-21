import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

/**
 * Room with doors that are locked until the plaer defeats all enemies
 */
public class BattleRoom extends Room{
    private Key key;
    private Door primaryDoor;
    private Door secondaryDoor;
    private Table table;
    private Basket basket;
    private KeyBulletKin keyBulletKin;
    private ArrayList<Point> keyBKdirection;
    private ArrayList<TreasureBox> treasureBoxes;
    private ArrayList<Wall> walls;
    private ArrayList<River> rivers;
    private ArrayList<BulletKin> BKs;
    private ArrayList<AshenBulletKin> AshenBKs;
    private final String nextRoomName;
    private final String roomName;
    private Store storerun = new Store();
    /**
     * Updating store status from other classes
     */
    public int storeflag = 0;

    /**
     * Constructor to create new BattleRoom
     * @param roomName current room
     * @param nextRoomName room on other side of door
     */
    public BattleRoom(String roomName, String nextRoomName) {
        keyBKdirection = new ArrayList<>();
        BKs = new ArrayList<>();
        AshenBKs = new ArrayList<>();
        walls = new ArrayList<>();
        rivers = new ArrayList<>();
        treasureBoxes = new ArrayList<>();
        this.roomName = roomName;
        this.nextRoomName = nextRoomName;
    }

    /**
     * initialise relevant fields dynamically
     * @param gameProperties all relevant game properties
     */
    public void initEntities(Properties gameProperties) {
        // find the configuration of game objects for this room
        for (Map.Entry<Object, Object> entry: gameProperties.entrySet()) {
            String roomSuffix = String.format(".%s", roomName);

            if (entry.getKey().toString().contains(roomSuffix)) {
                String objectType = entry.getKey().toString()
                        .substring(0, entry.getKey().toString().length() - roomSuffix.length());
                String propertyValue = entry.getValue().toString();

                // ignore if the value is 0
                if (propertyValue.equals("0")) {
                    continue;
                }

                String[] coordinates;
                for (String coords: propertyValue.split(";")) {
                    switch (objectType) {
                        case "primarydoor":
                            coordinates = propertyValue.split(",");
                            primaryDoor = new Door(IOUtils.parseCoords(propertyValue), coordinates[2], this);
                            break;
                        case "secondarydoor":
                            coordinates = propertyValue.split(",");
                            secondaryDoor = new Door(IOUtils.parseCoords(propertyValue), coordinates[2], this);
                            break;
                        case "keyBulletKin":
                            Point point = IOUtils.parseCoords(coords);
                            keyBKdirection.add(point);
                            break;
                        case "ashenBulletKin":
                            AshenBulletKin ashenBulletKin = new AshenBulletKin(IOUtils.parseCoords(coords));
                            AshenBKs.add(ashenBulletKin);
                            break;
                        case "bulletKin":
                            BulletKin bulletKin = new BulletKin(IOUtils.parseCoords(coords));
                            BKs.add(bulletKin);
                            break;
                        case "wall":
                            Wall wall = new Wall(IOUtils.parseCoords(coords));
                            walls.add(wall);
                            break;
                        case "treasurebox":
                            TreasureBox treasureBox = new TreasureBox(IOUtils.parseCoords(coords),
                                    Double.parseDouble(coords.split(",")[2]));
                            treasureBoxes.add(treasureBox);
                            break;
                        case "river":
                            River river = new River(IOUtils.parseCoords(coords));
                            rivers.add(river);
                            break;
                        case "table":
                            table = new Table(IOUtils.parseCoords(propertyValue));
                            break;
                        case "basket":
                            basket = new Basket(IOUtils.parseCoords(propertyValue));
                            break;
                        default:
                    }
                }
            }
        }
        keyBulletKin = new KeyBulletKin(keyBKdirection.get(0), keyBKdirection);
    }

    /**
     * Update the state of all objects contained within the battleroom
     * @param input key events from user
     */
    public void update(Input input) {
        // update and draw all active game objects in this room
        if (storeflag == 1){
            storefunc(this, input);
            return;
        }
        if (input.wasPressed(Keys.SPACE)){
            storeflag = 1;
            return;
        }

        primaryDoor.update(player);
        primaryDoor.draw();
        if (stopUpdatingEarlyIfNeeded()) {
            return;
        }
        secondaryDoor.update(player);
        secondaryDoor.draw();
        if (stopUpdatingEarlyIfNeeded()) {
            return;
        }
        if (!primaryDoor.isUnlocked()){
            bulletObstacleCollision(primaryDoor);
            EnemyFireballCollision(primaryDoor);
        }
        if (!secondaryDoor.isUnlocked()){
            bulletObstacleCollision(secondaryDoor);
            EnemyFireballCollision(secondaryDoor);
        }

        for (Wall wall: walls) {
            wall.update(player);
            wall.draw();
            bulletObstacleCollision(wall);
            EnemyFireballCollision(wall);
        }

        for (River river: rivers) {
            river.update(player);
            river.draw();
        }
        //check presence of ABKS
        for(AshenBulletKin enemy: AshenBKs){
            if (enemy.isActive()){
                enemy.update(player);
                enemy.draw();
                enemyBulletCollision(enemy);
            }
        }
        //check presence of bks
        for(BulletKin enemy: BKs){
            if (enemy.isActive()){
                enemy.update(player);
                enemy.draw();
                enemyBulletCollision(enemy);
            }
        }
        //check presence of KBK
        if (keyBulletKin.isActive()) {
            keyBulletKin.update(player);
            keyBulletKin.draw();
            enemyBulletCollision(keyBulletKin);
        }

        for (TreasureBox treasureBox: treasureBoxes) {
            if (treasureBox.isActive()) {
                treasureBox.update(input, player);
                treasureBox.draw();
            }
        }

        //key is only activated when KBK in not active
        if(key != null && key.isActive()) {
            key.update(player);
            key.draw();
        }

        if(table.isActive()){
            table.update(player);
            table.draw();
            bulletObstacleCollision(table);
            EnemyFireballCollision(table);
        }
        if(basket.isActive()){
            basket.update(player);
            basket.draw();
            bulletObstacleCollision(basket);
            EnemyFireballCollision(basket);
        }

        if (player != null) {
            player.update(input);
            player.draw();
        }

        if (noMoreEnemies() && !isComplete()) {
            setComplete(true);
            unlockAllDoors();
        }
    }

    /**
     * Set player status
     * @param player current player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * find the door by destination
     * @param roomName relevant room
     * @return the next locations
     */
    public Door findDoorByDestination(String roomName) {
        if (primaryDoor.toRoomName.equals(roomName)) {
            return primaryDoor;
        } else {
            return secondaryDoor;
        }
    }

    private void unlockAllDoors() {
        primaryDoor.unlock(false);
        secondaryDoor.unlock(false);
    }

    /**
     * Set status when enemies are gone
     * @param complete tracks game status
     */
    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    /**
     * initialise enemy objects
     */
    public void activateEnemies() {
        keyBulletKin.setActive(true);

        for(BulletKin enemy: BKs){
            enemy.setActive(true);
        }

        for(AshenBulletKin enemy: AshenBKs){
            enemy.setActive(true);
        }
    }

    /**
     * Check presence of enemies
     * @return Enemies left or not
     */
    public boolean noMoreEnemies() {
        return (keyBulletKin.isDead() && nomorebulletkin() && nomoreashenbulletkin());
    }

    /**
     * helper function for noMoreEnemies
     * @return status of BulletKins
     */
    public boolean nomorebulletkin(){
        for(BulletKin enemy: BKs){
            if (!enemy.isDead()){
                return false;
            }
        }
        return true;
    }

    /**
     * helper function for noMoreEnemies
     * @return status of AshenBulletKins
     */
    public boolean nomoreashenbulletkin(){
        for(AshenBulletKin enemy: AshenBKs){
            if (!enemy.isDead()){
                return false;
            }
        }
        return true;
    }

    /**
     * Implement enemy fireball collision logic if collision has happend
     * @param obstacle obstacle collided with
     */
    public void EnemyFireballCollision(Obstacle obstacle){
        //check every AshenBulletKin
        for (AshenBulletKin enemy: AshenBKs){
            fireballObstacleCollision(obstacle, enemy);
        }
        //check every bulletkin
        for (BulletKin enemy: BKs){
            fireballObstacleCollision(obstacle, enemy);
        }
    }

    /**
     * Helper function for EnemyFireballCollision
     * @param obstacle obstacle collided with
     * @param enemy type of enemy we are checking collision for
     */
    public void fireballObstacleCollision(Obstacle obstacle, Enemy enemy){
        //check all fireballs
        for(Fireball ball: enemy.fireBalls) {
            if (obstacle.projectilecollision(ball)) {
                ball.isActive = false;
                obstacle.Active = false;
            }
        }
    }

    /**
     * checking if bullet has collided with obstacle
     * @param obstacle obstacle collided with
     */
    public void bulletObstacleCollision(Obstacle obstacle){
        //iterate through all bullets
        for (Bullet bullet: player.gun.bullets){
            if (obstacle.projectilecollision(bullet)){
                bullet.isActive = false;
                obstacle.Active = false;
                if (obstacle instanceof Basket) {
                    player.earnCoins(20);
                }
            }
        }
    }

    /**
     * Check if player has shot enemy
     * @param enemy enemy collided with
     */
    public void enemyBulletCollision(Enemy enemy){
        //iterate through all projectiles
        for(Projectile projectile : player.gun.bullets){
            if (projectile.isActive && enemy.bulletcollision(projectile)){
                projectile.isActive = false;
                enemy.receiveDamage(player.gun.getDamage());
                if (enemy.health <= 0){
                    player.earnCoins(enemy.coinDrop);
                    if (player instanceof Robot){
                        player.earnCoins(5);
                    }
                    enemy.dead = true;
                    enemy.active = false;
                    if (enemy instanceof KeyBulletKin){
                        key = new Key(keyBulletKin.position);
                    }
                }
            }
        }
    }

    /**
     * Update screen objects so it is paused when store is activated
     * @param curr current Room object-used for debugging
     * @param input player key events
     */
    private void storefunc(BattleRoom curr, Input input){
        primaryDoor.draw();
        secondaryDoor.draw();

        //update all object states for the pause
        if(basket.isActive()) {
            basket.update(player);
            basket.draw();
        }
        if(table.isActive()) {
            table.update(player);
            table.draw();
        }

        for (Wall wall: walls) {
            wall.update(player);
            wall.draw();
        }
        for (River river: rivers) {
            river.update(player);
            river.draw();
        }
        if (player != null){
            player.draw();
        }
        for(AshenBulletKin enemy: AshenBKs){
            if (enemy.isActive()){
                enemy.draw();
            }
        }
        for(BulletKin enemy: BKs){
            if (enemy.isActive()){
                enemy.draw();
            }
        }
        if (keyBulletKin.isActive()) {
            keyBulletKin.draw();
        }

        storerun.update(input, player, this);
        storerun.draw();

    }



}
