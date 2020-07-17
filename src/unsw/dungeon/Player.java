package unsw.dungeon;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {

    private Dungeon dungeon;
    private String recentMovement;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y, false);
        recentMovement = "";
        this.dungeon = dungeon;
    }

    public void notifyDungeon() {
        dungeon.update(this);
    }

    @Override
    public void update(Entity entity) {
        if (entity instanceof Boulder) {
            Boulder b = (Boulder) entity;  
            updatePosition();         
        }    
    }

    public void moveUp() {
        if (getY() > 0) {
            recentMovement = "up";
            /*
            if (!willCollide(getX(), getY()-1)) {
                y().set(getY() - 1);
            }
            else {
                notifyDungeon();
            }  */
            notifyDungeon();
        }   
    }

    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1) {
            recentMovement = "down";
            /*
            if (!willCollide(getX(), getY()+1)) {
                y().set(getY() + 1);
            } 
            else {
                notifyDungeon();
            }          */
            notifyDungeon();
        }   
    }

    public void moveLeft() {
        if (getX() > 0) {
            recentMovement = "left";
            /*if (!willCollide(getX() - 1, getY())) {
                x().set(getX() - 1);
            }
            
            else {
                notifyDungeon();
            }  */
            notifyDungeon();
        }          
    }
    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1) {
            recentMovement = "right";
            /*if (!willCollide(getX() + 1, getY())) {
                x().set(getX() + 1);
            }
            else {
                notifyDungeon();
            } */  
            notifyDungeon();
        }
    }

    public String getRecentMovement() {
        return recentMovement;
    }

    public boolean willCollide(int x, int y) {
        return dungeon.willCollide(x, y);
    }
    /*
    public boolean willCollide(Entity entity) {
        int entityX = entity.getX();
        int entityY = entity.getY();
        
        if  (recentMovement == "up") { 
            if (entityX == getX() && entityY == getY() - 1) {
                return dungeon.willCollide(entityX, entityY);
                    
            }
        }
        else if (recentMovement == "down") { 
            if (entityX == getX() && entity.getY() == getY() + 1) {
                return dungeon.willCollide(entityX, entityY);
            }
        }
        else if  (recentMovement == "right") { 
            if (entityX == getX() + 1 && entityY == getY()) {
                    return dungeon.willCollide(entityX, entityY);
            }
        }
        else if (recentMovement == "left") { 
            if (entityX == getX() - 1 && entity.getY() == getY()) {
                return dungeon.willCollide(entityX, entityY);
            }
        }
        return false;
    }*/

    public Entity getAdjacentObj() {
        
        if  (recentMovement == "up") { 
            return dungeon.adjacentObj(getX(), getY() - 1);
        }
        else if (recentMovement == "down") { 
            return dungeon.adjacentObj(getX(), getY() + 1);
        }
        else if  (recentMovement == "right") { 
            return dungeon.adjacentObj(getX() + 1, getY());          
        }
        else if (recentMovement == "left") { 
            return dungeon.adjacentObj(getX() - 1, getY());
        }
        return null;
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
}

