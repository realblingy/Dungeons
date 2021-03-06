package unsw.dungeon;

import java.io.IOException;

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