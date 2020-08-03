package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class DifficultyMenuController extends Controller {
    
    private String difficulty;

    public DifficultyMenuController(DungeonApplication application) {
        super(application);
    }

    @FXML
    public void handleBtn(ActionEvent event) {
        difficulty = ((Node) event.getSource()).getId();
        notifyApplication();
    } 

    public String getDifficulty() {
        return difficulty;
    }

    public void notifyApplication() {
        try {
            super.getDungeonApplication().update(this);
        } catch (IOException e) {
            System.out.println("Could not change difficulty.");
        }
        
    }

}