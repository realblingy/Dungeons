/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon implements DungeonObserver{

    private int width, height;
    private List<Entity> entities;
    private Player player;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
    }
    
    @Override
    public void update(Entity entity) {
        if (entity instanceof Player) {
            update( (Player) entity);
        }

        if (entity instanceof Boulder) {
            update ( (Boulder) entity);
        }
    
    }

    public void update(Player player) {
        for (Entity obj : entities) {
            // For walls and boulders
            if (player.willCollide(obj) && !player.equals(obj)) {
                int oldX = obj.getX();
                int oldY = obj.getY();
                // push if obj is a boulder 
                if (obj.getClass() == Boulder.class) {
                    obj.update(player);
                    // if the boulder is pushed
                    if (donePush(player, oldX, oldY)) {
                        // trigger the new Switch(use obj coordinates) and shut off the old one(use player coordinates)
                        triggerAfterPush(obj, player);
                    }
                }
            }
            // For potions, items, etc..
            else if (!player.willCollide(obj) && !player.equals(obj)) {
                player.update(obj);
            }
        }
    }

    // public void update(Boulder boulder) {
    //     for (Entity obj : entities) {
    //         if (boulder.willCollide(obj) && !boulder.equals(obj)) {
    //             boulder.update(obj);
    //         }
    //     }
    // }

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

    public void addEntity(Entity entity) {
        entities.add(entity);
        TriggerAtBeginning(entity);
    }

    /**
     * method for triggering switch at the start of the game when the boulder entity is added on the top of the switch
     * @param entity
     */
    public void TriggerAtBeginning (Entity entity) {
        if (entity.getClass() == Boulder.class) {
            int index = onSwitch(entity.getX(), entity.getY());
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
    public void triggerAfterPush (Entity obj, Player player) {
        int Old = onSwitch(player.getX(), player.getY());
        int New = onSwitch(obj.getX(), obj.getY());
        // shut off the switch at the old position of Boulder
        if (Old != -1) {
            Switch oldSwitch = (Switch) entities.get(Old);
            oldSwitch.update(player);
        }
        // trigger the switch at the postion of the Boulder after push
        if (New != -1) {
            Switch newSwitch = (Switch) entities.get(New);
            newSwitch.update(obj);
            if (allTrigger()) {
                System.out.println("All the Floor Switches are triggered");
            }
        }
    }

    /**
     * method for checking if the Boulder is pushed
     * @param player    player entity (player is now at the old position of the Boulder after pushing it)
     * @param x         x coordinate of Boulder before push
     * @param y         y coordinate of Boulder before push
     * @return true if the Boulder is pushed else return false
     */
    public boolean donePush(Player player, int x, int y) {
        if (player.getX() == x && player.getY() == y) {
            System.out.println("Done Push !!!");
            return true;
        }
        return false;
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

        if (entitiesInOneSquare.size() > 1) {
            for (Entity entity : entitiesInOneSquare) {
                // If any of the entities are boulders return true
                if (entity.getClass() == Boulder.class) {
                    return true;
                }
            }
        }
        // If there is only one entitiy
        else if (entitiesInOneSquare.size() == 1) {
            // If it is not collidable?
            if (!entitiesInOneSquare.get(0).isCollidable()) {
                return true;
            }
        }

        return false;
    }

    /**
     * method for checking if the square with coordinates(x,y) contains switch
     * @param x
     * @param y
     * @return entity index if the square contains switch else return -1
     */
    public int onSwitch(int x, int y) {
        List<Integer> index = entityIndex(x, y);
        for (Integer i : index) {
            Entity entity = entities.get(i);
            if (entity.getClass() == Switch.class) {
                return i;
            }
        }
        return -1;
    }

    public boolean allTrigger() {
        for (Entity e : entities) {
            if (e.getClass() == Switch.class) {
                Switch s = (Switch) e;
                if (s.getTrigger() == 0) {
                    return false;
                }
            }
        }
        return true;
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
            if (entity != null) {
                if (entity.getX() == x && entity.getY() == y) {
                    entitiesInOneSquare.add(i);
                }
            }
        }
        return entitiesInOneSquare;
    }

}
