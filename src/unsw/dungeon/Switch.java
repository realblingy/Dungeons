package unsw.dungeon;

public class Switch extends Entity {
    
    private int trigger;

    /**
     * Create a floor switch positioned on square(x, y)
     * @param x
     * @param y
     */
    public Switch (int x, int y) {
        super(x, y, true);
        // first shut off the switch
        this.trigger = 0;   
    }

    public void triggerSwitch () {
        this.trigger = 1;
        
    }

}