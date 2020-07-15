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
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private Player player;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
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

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public boolean willCollide(int x, int y) {
        /*for (Entity entity : entities) {
            if (entity != null) {
                if (entity.getX() == x && entity.getY() == y && !entity.isCollidable()) return true;
            }

        }*/

        List<Entity> entitiesInOneSquare = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getX() == x && entity.getY() == y) {
                entitiesInOneSquare.add(entity);
            }
        }

        if (entitiesInOneSquare.size() > 1) {
            for (Entity entity : entitiesInOneSquare) {
                if (entity.getClass() == Boulder.class) {
                    return true;
                }
            }
        }
        else if (entitiesInOneSquare.size() == 1) {
            if (!entitiesInOneSquare.get(0).isCollidable()) {
                return true;
            }
        }

        return false;
    }

    
    public int isBoulder(int x, int y) {
        List<Integer> index = entityIndex(x, y);
        for (Integer i : index) {
            Entity entity = entities.get(i);
            if (entity.getClass() == Boulder.class) {
                return i;
            }
        }
        return -1;
    }

    public void pushBoulder(int x, int y, String move) {
        if (isBoulder(x, y) != -1) {
            int index = isBoulder(x, y);
            if (index != -1) {
                Boulder b = (Boulder) entities.get(index);
                if (move == "left" && !willCollide(x - 1, y)) {
                    b.resetPosition(x - 1, y);
                    player.x().set(x);
                }
                if (move == "right" && !willCollide(x + 1, y)) {
                    b.resetPosition(x + 1, y);
                    player.x().set(x);
                }
                if (move == "up" && !willCollide(x, y - 1)) {
                    b.resetPosition(x, y - 1);
                    player.y().set(y);
                }
                if (move == "down" && !willCollide(x, y + 1)) {
                    b.resetPosition(x, y + 1);
                    player.y().set(y);
                }
            }
        }
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
