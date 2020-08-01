package unsw.dungeon;

import java.io.IOException;

import javax.swing.Action;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;


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
    public void handlePlayBtnHover() {
        System.out.println("Hi!");
        playBtn.setStyle("-fx-background-color:gray");
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