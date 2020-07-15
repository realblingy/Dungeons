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
                obj.update(player);
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
    }

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
