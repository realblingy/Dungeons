package unsw.dungeon;

public class PauseMenu {

    private boolean exit;
    private boolean returnToMenu;
    
    public PauseMenu() {
        exit = false;
        returnToMenu = false;
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public boolean isReturnToMenu() {
        return returnToMenu;
    }

    public void setReturnToMenu(boolean returnToMenu) {
        this.returnToMenu = returnToMenu;
    }

    
    
}