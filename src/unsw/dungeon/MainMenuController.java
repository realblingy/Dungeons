package unsw.dungeon;

import java.io.IOException;

import javax.swing.Action;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class MainMenuController extends Controller {

    private String action;

    public MainMenuController(DungeonApplication application) {
        super(application);
    }

    @Override
    public void notifyApplication() throws IOException {
        super.getDungeonApplication().update(this);
    }

    public String getAction() {
        return action;
    }

    @FXML
    public void handlePlayBtn(ActionEvent event) throws IOException {
        action = "play";
        notifyApplication();
    }

    @FXML
    public void handleMapsBtn(ActionEvent event) throws IOException {
        action = "maps";
        notifyApplication();
    }
    
}