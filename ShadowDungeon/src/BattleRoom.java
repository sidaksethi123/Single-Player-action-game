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
    public int storeflag = 0;
    private Store storerun = new Store();

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

        for(AshenBulletKin enemy: AshenBKs){
            if (enemy.isActive()){
                enemy.update(player);
                enemy.draw();

                for(Projectile projectile : player.gun.bullets){
                    if (projectile.isActive && enemy.bulletcollision(projectile)){
                        projectile.isActive = false;
                        enemy.health -= player.gun.damage;
                        if (enemy.health <= 0){
                            player.coins += enemy.coinDrop;
                            enemy.dead = true;
                            enemy.active = false;
                        }
                    }
                }
            }
        }

        for(BulletKin enemy: BKs){
            if (enemy.isActive()){
                enemy.update(player);
                enemy.draw();

                for(Projectile projectile : player.gun.bullets){
                    if (projectile.isActive && enemy.bulletcollision(projectile)){
                        projectile.isActive = false;
                        enemy.health -= player.gun.damage;
                        if (enemy.health <= 0){
                            player.coins += enemy.coinDrop;
                            enemy.dead = true;
                            enemy.active = false;
                        }
                    }
                }
            }
        }


        if (keyBulletKin.isActive()) {
            keyBulletKin.update(player);
            keyBulletKin.draw();

            for(Projectile projectile : player.gun.bullets){
                if (projectile.isActive && keyBulletKin.bulletcollision(projectile)){
                    projectile.isActive = false;
                    keyBulletKin.health -= player.gun.damage;
                    if (keyBulletKin.health <= 0){
                        keyBulletKin.dead = true;
                        keyBulletKin.active = false;
                        key = new Key(keyBulletKin.position);
                    }
                }
            }
        }

        for (TreasureBox treasureBox: treasureBoxes) {
            if (treasureBox.isActive()) {
                treasureBox.update(input, player);
                treasureBox.draw();
            }
        }

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




    public void setPlayer(Player player) {
        this.player = player;
    }

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


    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void activateEnemies() {
        keyBulletKin.setActive(true);

        for(BulletKin enemy: BKs){
            enemy.setActive(true);
        }

        for(AshenBulletKin enemy: AshenBKs){
            enemy.setActive(true);
        }
    }

    public boolean noMoreEnemies() {
        return (keyBulletKin.isDead() && nomorebulletkin() && nomoreashenbulletkin());
    }

    public boolean nomorebulletkin(){
        for(BulletKin enemy: BKs){
            if (!enemy.isDead()){
                return false;
            }
        }
        return true;
    }

    public boolean nomoreashenbulletkin(){
        for(AshenBulletKin enemy: AshenBKs){
            if (!enemy.isDead()){
                return false;
            }
        }
        return true;
    }

    public void EnemyFireballCollision(Obstacle obstacle){
        for (AshenBulletKin enemy: AshenBKs){
            fireballObstacleCollision(obstacle, enemy);
        }
        for (BulletKin enemy: BKs){
            fireballObstacleCollision(obstacle, enemy);
        }
    }

    public void fireballObstacleCollision(Obstacle obstacle, Enemy enemy){
        for(Fireball ball: enemy.fireBalls) {
            if (obstacle.fireballcollision(ball)) {
                ball.isActive = false;
                obstacle.Active = false;
            }
        }
    }

    public void bulletObstacleCollision(Obstacle obstacle){
        for (Bullet bullet: player.gun.bullets){
            if (obstacle.bulletcollision(bullet)){
                bullet.isActive = false;
                obstacle.Active = false;
                if (obstacle instanceof Basket) {
                    player.earnCoins(20);
                }
            }
        }
    }


    private void storefunc(BattleRoom curr, Input input){
        if(basket.isActive()) {
            basket.update(player);
            basket.draw();
        }
        for (Wall wall: walls) {
            wall.update(player);
            wall.draw();
        }
        for (River river: rivers) {
            river.update(player);
            river.draw();
        }
        storerun.update(input, player, this);
        storerun.draw();

    }


}
