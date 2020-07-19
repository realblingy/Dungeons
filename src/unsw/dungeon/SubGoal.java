package unsw.dungeon;

public class SubGoal implements Goal {

    private String mainGoal;
    private boolean complete;
    
    public SubGoal(String mainGoal) {
        this.mainGoal = mainGoal;
    }

    public String getGoal() {
        return mainGoal;
    }

    public boolean isComplete() {
        return complete;
    }

    public void complete() {
        complete = true;
    }
}