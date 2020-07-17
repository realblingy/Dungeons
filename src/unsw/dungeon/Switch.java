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

    public int getTrigger() {
        return trigger;
    }

    @Override
    public void update(Entity entity) {
        if (entity instanceof Boulder) {
            Boulder boulder = (Boulder) entity;
            setTrigger(boulder);
        }
    }

    /**
     * method for triggering switch after pushBoulder 
     * if the switch is already trigger then do nothing
     * if the switch is off then trigger the switch
     */
    public void setTrigger(Boulder boulder) {
        if (trigger == 0 && getX() == boulder.getX() && getY() == boulder.getY()) {
            this.trigger = 1;
            System.out.println("switch ("+getX()+" , "+getY()+" ) "+" is trigger");
        }

        if (trigger == 1) {
            if (boulder.getX() != getX() || boulder.getY() != getY()) {
                this.trigger = 0;
                System.out.println("switch ("+getX()+" , "+getY()+" ) "+" is shut off");
            }
        }
    }

    /**
     * method for shut off the switch after pushBoulder
     * therefore now the player is now at the old boulder position 
     * meaing the boulder is pushed off the switch 
     */
    public void shutOffTrigger() {
        this.trigger = 0;
        System.out.println("switch ("+getX()+" , "+getY()+" ) "+" is shut off");
    }
}