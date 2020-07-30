package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;

public abstract class Controller {

    private DungeonApplication application;

    public Controller(DungeonApplication application) {
        this.application = application;
    }

    public DungeonApplication getDungeonApplication() {
        return application;
    }

    public abstract void notifyApplication() throws IOException;
}