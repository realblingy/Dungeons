package unsw.dungeon;

import javafx.application.Application;

public class Controller {

    private DungeonApplication application;

    public Controller(DungeonApplication application) {
        this.application = application;
    }

    public DungeonApplication getApplication() {
        return application;
    }
}