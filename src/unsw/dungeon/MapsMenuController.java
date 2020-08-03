package unsw.dungeon;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MapsMenuController extends Controller {

    @FXML
    private Pane pane;

    @FXML
    private ImageView advanced;

    @FXML
    private ImageView boulders;

    @FXML
    private ImageView maze;

    private String dungeon;

    public MapsMenuController(DungeonApplication application) {
        super(application);
    }

    @Override
    public void notifyApplication() throws IOException {
        // TODO Auto-generated method stub
        super.getDungeonApplication().update(this);
    }

    public String getDungeon() {
        return dungeon;
    }

    @FXML
    public void initialize() {
        // ImageView iv = new ImageView();
        advanced.setImage( new Image((new File("examples/advanced.png")).toURI().toString()) );
        boulders.setImage( new Image((new File("examples/boulders.png")).toURI().toString()) );
        maze.setImage( new Image((new File("examples/maze.png")).toURI().toString()) );
    }

    @FXML
    public void handleAdvancedImage(MouseEvent event) {
        dungeon = ((ImageView) event.getSource()).getId();
        try {
            notifyApplication();
        }
        catch (IOException e) {
            System.out.println("MapsMenuController could not notify application.");
        }
        
    }
    
}