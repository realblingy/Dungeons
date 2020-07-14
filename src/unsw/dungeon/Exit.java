package unsw.dungeon;

public class Exit extends Entity {
    
    private boolean onExit;

    public Exit(int x, int y) {
        super(x, y, true);
        onExit = false;
    }

    public boolean playerExits(int x, int y) {
        if (getX() == x && getY() == y) { onExit = true; }
        return onExit;
    }

}