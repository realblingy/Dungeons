package unsw.dungeon;

import java.util.List;
import java.util.ArrayList;

public class MainGoal implements Goal {

    private String mainGoal;
    private List<Goal> subGoals;
    private boolean complete;

    public MainGoal(String mainGoal) {
        this.mainGoal = mainGoal;
        subGoals = new ArrayList<Goal>();
        complete = false;
    }

    public void add(Goal goal) {
        subGoals.add(goal);
    }

    public String getGoal() {
        return mainGoal;
    }

    public boolean isComplete() {
        return complete;
    }

    public void complete() {
        for (Goal goal : subGoals) {
            if (!goal.isComplete()) {
                return;
            }
        }
        complete = true;
    }
}