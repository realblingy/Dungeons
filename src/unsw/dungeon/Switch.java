package unsw.dungeon;

import java.util.List;

public class Switch extends Entity {
    private Dungeon dungeon;
    private int trigger;

    /**
     * Create a floor switch positioned on square(x, y)
     * @param x
     * @param y
     */
    public Switch (Dungeon dungeon, int x, int y) {
        super(x, y, true);
        // first shut off the switch
        this.trigger = 0;   
        this.dungeon = dungeon;
    }

    public void notifyDungeon() {
        dungeon.update(this);
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
     * method to check if all the switches are triggered
     * @return true if all triggered, else false
     */
    public boolean allTrigger() {
        List<Entity> entities = dungeon.getEntities();
        for (Entity e : entities) {
            if (e.getClass() == getClass()) {
                Switch s = (Switch) e;
                if (s.getTrigger() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * method for triggering switch after pushBoulder 
     * shut off the switch if the boulder is pushed off 
     * trigger the switch if the boulder is pushed onto it
     */
    public void setTrigger(Boulder boulder) {
        if (trigger == 0 && getX() == boulder.getX() && getY() == boulder.getY()) {
            this.trigger = 1;
            System.out.println("switch ("+getX()+" , "+getY()+" ) "+" is trigger");
            if (allTrigger()) {
                notifyDungeon();
            }
        }

        if (trigger == 1) {
            if (boulder.getX() != getX() || boulder.getY() != getY()) {
                this.trigger = 0;
                System.out.println("switch ("+getX()+" , "+getY()+" ) "+" is shut off");
            }
        }
    }
}