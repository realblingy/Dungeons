package unsw.dungeon;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MapsMenuController extends Controller {

    @FXML
    private Pane pane;

    @FXML
    private ImageView advanced;

    public MapsMenuController(DungeonApplication application) {
        super(application);
    }

    @Override
    public void notifyApplication() throws IOException {
        // TODO Auto-generated method stub
        return;
    }

    @FXML
    public void initialize() {
        // ImageView iv = new ImageView();
        advanced.setImage( new Image((new File("examples/advanced.png")).toURI().toString()) );
    }

    @FXML
    public void handleAdvancedImage(MouseEvent event) {
        System.out.println("ADVANCED");
    }
    
}