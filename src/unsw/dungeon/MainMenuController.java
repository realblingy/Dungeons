package unsw.dungeon;

import java.io.IOException;

import javax.swing.Action;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Node;

public class MainMenuController extends Controller {

    private String action;

    @FXML
    private Button playBtn;

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
    public void handleBtn(ActionEvent event) throws IOException {
        action = ((Node) event.getSource()).getId();
        notifyApplication();
    } 
    
}