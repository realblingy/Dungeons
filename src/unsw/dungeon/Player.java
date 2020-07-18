package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {

    private Dungeon dungeon;
    private String recentMovement;
    private List<Item> inventory;
    private boolean invincible;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y, false);
        recentMovement = "";
        this.dungeon = dungeon;
        this.inventory = new ArrayList<>();
    }

    public void notifyDungeon() {
        dungeon.update(this);
    }

    @Override
    public void update(Entity entity) {
        if (entity instanceof Boulder) {
            updatePosition();         
        }    
    }

    public void moveUp() {
        if (getY() > 0) {
            recentMovement = "up";
            notifyDungeon();
        }   
    }

    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1) {
            recentMovement = "down";
            notifyDungeon();
        }   
    }

    public void moveLeft() {
        if (getX() > 0) {
            recentMovement = "left";
            notifyDungeon();
        }          
    }
    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1) {
            recentMovement = "right";
            notifyDungeon();
        }
    }

    public String getRecentMovement() {
        return recentMovement;
    }

    public void updatePosition() {
        if (recentMovement == "up") {
            y().set(getY() - 1);
        }
        if (recentMovement == "down") {
            y().set(getY() + 1);
        }
        if (recentMovement == "left") {
            x().set(getX() - 1);
        }
        if (recentMovement == "right") {
            x().set(getX() + 1);
        }
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public void move(int x, int y) {
        x().set(x);
        y().set(y);
    }

    /**
     * method to find the key of a specific door with id
     * @param id
     * @return  key if found else return null
     */
    public Item findKey(int id) {
        // there will be only one key in inventory at all time
        for (Item item : inventory) {
            if (item instanceof DungeonKey) {
                DungeonKey key = (DungeonKey) item;
                if (key.getID() == id) {
                    return key;
                }
            }
        }
        return null;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void activateInvincibility() {
        invincible = true;
        Thread newThread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                invincible = false;
                System.out.println(invincible);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        newThread.start();
    }

    /**
     * method to determine if the player can pick up the item or not
     * @param requiredPickUp    item that is required to be picked up
     * @return  true if the item can be picked up else false
     */
    public boolean canPickUp(Item requiredPickUp) {
        int n_key = 0;
        int n_sword = 0;
        if (requiredPickUp instanceof Potion) {
            return true;
        }
        for (Item item : inventory) {
            if (item instanceof DungeonKey) {
                n_key++;
            }
            if (item instanceof Sword) {
                n_sword++;
            }
        }
        if ((n_key != 0 && (requiredPickUp instanceof DungeonKey)) ||
            (n_sword != 0 && (requiredPickUp instanceof Sword))) {
            return false;
        }
        return true;
    }
}

