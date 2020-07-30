package unsw.dungeon;

import java.io.IOException;

import javax.swing.Action;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainMenuController extends Controller {

    public MainMenuController(DungeonApplication application) {
        super(application);
    }

    @FXML
    public void handlePlayBtn(ActionEvent event) throws IOException {
        System.out.println("Play!");
        // super.getApplication().changeScene("MainMenu");
    }

    @FXML
    public void handleMapsBtn(ActionEvent event) throws IOException {
        super.getApplication().changeScene("MapsMenu");
    }
    
}