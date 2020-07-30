package unsw.dungeon;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class MapsMenuController extends Controller {

    public MapsMenuController(DungeonApplication application) {
        super(application);
    }

    @FXML
    public void handleAdvancedImage(MouseEvent event) {
        System.out.println("ADVANCED");
    }
    
}