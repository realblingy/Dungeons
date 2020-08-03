package unsw.dungeon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.scene.image.Image;

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
    private boolean canMove;
    private boolean hasMove;

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
        canMove = true;
        hasMove = false;
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
        if (getY() > 0 && canMove) {
            recentMovement = "up";
            notifyDungeon();
        }   
    }

    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1 && canMove) {
            recentMovement = "down";
            notifyDungeon();
        }   
    }

    public void moveLeft() {
        if (getX() > 0 && canMove) {
            recentMovement = "left";
            notifyDungeon();
        }          
    }
    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1 && canMove) {
            recentMovement = "right";
            notifyDungeon();
        }
    }

    public String getRecentMovement() {
        return recentMovement;
    }
    
    public boolean checkMove() {
        return hasMove;
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
        if (hasMove == false) {
            hasMove = true;
            dungeon.stopGoalConditionDisplay();
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

    public boolean canMove() {
        return canMove;
    }

    public void setMove(boolean canMove) {
        this.canMove = canMove;
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

    public boolean reduceSwordhits() {
        for (Item item : inventory) {
            if (item instanceof Sword) {
                Sword sword = (Sword) item;
                sword.reduceHits();
                if (sword.getHits() == 0) {
                    inventory.remove(item);
                }
                return true;
            }
        }
        return false;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void changePlayerImage() {
        dungeon.changeEntityImage(this);
    }

    public void activateInvincibility() {
        invincible = true;
        changePlayerImage();
        Thread newThread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                invincible = false;
                System.out.println(invincible);
                changePlayerImage();
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

