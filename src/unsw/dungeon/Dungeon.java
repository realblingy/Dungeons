/**
 *
 */
package unsw.dungeon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import javafx.scene.image.Image;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon implements DungeonObserver {

    private int width, height;
    private List<Entity> entities;
    private Player player;
    private Enemy enemy;
    private DungeonLoader dungeonLoader;
    private DungeonController dungeonController;

    public Dungeon(DungeonLoader dungeonLoader, int width, int height, JSONObject goal) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.enemy = null;
        this.dungeonLoader = dungeonLoader;
        this.dungeonController = null;
    }

    public void setController(DungeonController controller) {
        this.dungeonController = controller;
    }

    public void removeEntity(Entity entity) {
        try {
            dungeonLoader.removeEntity(entity);
        } catch (NullPointerException e) {

        }
    }

    @Override
    public void update(Entity entity) {
        if (entity instanceof Player) {
            update((Player) entity);
        }

        if (entity instanceof Boulder) {
            update((Boulder) entity);
        }

        if (entity instanceof Switch) {
            update((Switch) entity);
        }

        if (entity instanceof Exit) {
            update((Exit) entity);
        }

        if (entity instanceof Item) {
            update((Item) entity);
        }

        if (entity instanceof Door) {
            update((Door) entity);
        }

        if (entity instanceof Entity) {
            update((Enemy) entity);
        }
    }

    public void update(Player player) {

        Entity obj = getAdjacentObj(player);
        if (obj == null) {
            player.updatePosition();
            if (enemy != null) {
                enemy.updatePosition(player);
            }
        } else {
            if (!(obj instanceof Enemy) && enemy != null) {
                obj.update(player);
                enemy.updatePosition(player);
            } else {
                obj.update(player);
            }
        }
    }

    public void update(Boulder boulder) {
        player.update(boulder);
        UpdateOldAndNewSwitch(boulder, player);

    }

    public void changeEntityImage(Entity entity) {
        dungeonLoader.changeEntityImage(entity);
    }

    public void update(Exit exit) {
        player.move(exit.getX(), exit.getY());
        player.setMove(false);
        // use loadNextGame(string) to load next json file (string = filename)
        dungeonController.gameComplete(true);

    }

    public void update(Switch switchPlate) {
        player.setMove(false);
        dungeonController.gameComplete(true);

    }

    public void update(Item item) {
        removeEntity(item);
        removeDungeonEntity(item);
        if (item instanceof Treasure) {
            if (enemy == null && treasureInDungeon() == false) {
                player.setMove(false);
                dungeonController.gameComplete(true);        
            }
        }
    }

    public void update(Door door) {
        removeEntity(door);
        player.updatePosition();
    }

    public void update(Enemy enemy) {
        if (player.canMove() == false) {
            dungeonController.FailMenu(true);
        }
        else {
            removeEntity(enemy);
            this.enemy = null;
            removeDungeonEntity(enemy);
            if (treasureInDungeon() == false) {
                player.setMove(false);
                dungeonController.gameComplete(true);             
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
        TriggerAtBeginning(entity);
    }
    
    public void removeDungeonEntity(Entity entity) {
         entities.remove(entity);
    }

    public void stopGoalConditionDisplay() {
        dungeonController.dungeonMapGoal(false);
    }

    public void loadNextGame(String string) {
        if (dungeonController != null) {
            try {
                dungeonController.resetDungeonMap(string);
                dungeonController.notifyApplication();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
    //  * method for triggering switch at the start of the game when the boulder entity is added on the top of the switch
    //  * @param entity
    //  */
    public void TriggerAtBeginning (Entity entity) {
        if (entity.getClass() == Boulder.class) {
            int index = findSwitch(entity.getX(), entity.getY());

            if (index != -1) { 
                Switch s = (Switch) entities.get(index);
                s.update(entity);
            }
        }
    }

    /**
     * method for updateing switch trigger or shut off once the boulder is pushed
     * @param obj
     * @param player
     */
    public void UpdateOldAndNewSwitch (Boulder obj, Player player) {
        int Old = findSwitch(player.getX(), player.getY());
        int New = findSwitch(obj.getX(), obj.getY());
        // shut off the switch at the old position of Boulder
        if (Old != -1) {
            Switch oldSwitch = (Switch) entities.get(Old);
            oldSwitch.update(obj);
        }
        // trigger the switch at the postion of the Boulder after push
        if (New != -1) {
            Switch newSwitch = (Switch) entities.get(New);
            newSwitch.update(obj);
        }
    }

    /**
     * method for checking if square(x,y) contains collidable item
     * @param x
     * @param y
     * @return true if player cannot move into that square else return false
     */
    public boolean willCollide(int x, int y) {

        List<Entity> entitiesInOneSquare = new ArrayList<>();
        for (Entity entity : entities) {
            // Checks if player's coordinates are the same as entities
            if (entity.getX() == x && entity.getY() == y) {
                entitiesInOneSquare.add(entity);
            }
        }

        // the Square(x, y) contains only one entitiy
        if (entitiesInOneSquare.size() == 1) {
            //If it is not collidable?
            Entity entity = entitiesInOneSquare.get(0);
            if (!entity.isCollidable()) {
                return true;
            }
        }

        // the Square(x, y) contains more than one entities
        else if (entitiesInOneSquare.size() > 1) {
            for (Entity entity : entitiesInOneSquare) {
                // If any of the entities are boulders return true
                if (entity instanceof Boulder) {
                    return true;
                }
            }
        }

        // the Square(x, y) does not contain any entity (is empty)
        return false;
    }

    /**
     * method to find the index of Switch if exist in Square(x, y)
     * @param x
     * @param y
     * @return index of switch else return null
     */
    public int findSwitch(int x, int y) {
        List<Integer> index = entityIndex(x, y);
        for (Integer i : index) {
            Entity entity = entities.get(i);
            if (entity instanceof Switch) {
                return i;
            }
        }
        return -1;
    }

    /**
     * method for collecting entities in one square(x,y)
     * @param x
     * @param y
     * @return list of entities' indexes in one square
     */
    public List<Integer> entityIndex(int x, int y) {
        List<Integer> entitiesInOneSquare = new ArrayList<>();

        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity.getX() == x && entity.getY() == y) {
                entitiesInOneSquare.add(i);
            }
        }
        return entitiesInOneSquare;
    }

    /**
     * method to find uncollidable adjacentObj from list of entities 
     * @param x
     * @param y
     * @return entity if found else return null
     */
    public Entity adjacentObj(int x, int y) {
        for (Entity e : entities) {
            if (e != null) {
                //if (e.getX() == x && e.getY() == y && !e.isCollidable()) {
                if (e.getX() == x && e.getY() == y && !(e instanceof Switch)) {
                    return e;
                }
            }
        }
        return null;
    }

    public List<Entity> getEntities() {
        return entities;
    }


    /**
     * method to find adjacent entity of the entity(player or enemy) given (based on the movement)
     * @param entity
     * @return return adjacent entity if found else return null
     */
    public Entity getAdjacentObj(Entity entity) {
        String recentMovement = "";
        int x = -1;
        int y = -1;
        if (entity instanceof Player) {
            Player player = (Player) entity;
            recentMovement = player.getRecentMovement();
            x = player.getX();
            y = player.getY();
        }        

        if  (recentMovement == "up") { 
            return adjacentObj(x, y - 1);
        }
        else if (recentMovement == "down") { 
            return adjacentObj(x, y + 1);
        }
        else if  (recentMovement == "right") { 
            return adjacentObj(x + 1, y);          
        }
        else if (recentMovement == "left") { 
            return adjacentObj(x - 1, y);
        }
        return null;
    }

    public Portal matchingPortal(Portal portal) {
        for (Entity entity : entities) {
            if (entity instanceof Portal) {
                Portal potentialMatch = (Portal) entity;
                if (portal.matchPortal(potentialMatch)) {
                    return potentialMatch;
                } 
            }
        }
        return null;
    } 

    public boolean entityAtLocation(int x, int y) {
        for (Entity entity : entities) {
            if (entity.getX() == x && entity.getY() == entity.getY()) { return true; }
        }
        return false;
    }

    public void movePlayer(int x, int y) {
        player.move(x, y);
    }

    public void assignPlayer(Player player) {
        this.player = player;
    }

    public boolean hasEntity(Entity entity) {
        for (Entity e : entities) {
            if (e.equals(entity)) return true;
        }
        return false;
    }

    public boolean treasureInDungeon() {
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (e instanceof Treasure) {
                return true;
            }
        }
        return false;
    }
}
