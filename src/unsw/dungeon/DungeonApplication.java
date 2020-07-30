package unsw.dungeon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

    private String dungeonMap;
    private Stage stage;
    private HashMap<String, FXMLLoader> loaders; 

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        stage.setTitle("Dungeon");
        ArrayList<String> scenes = new ArrayList<String>();
        scenes.addAll(Arrays.asList("MainMenu", "MapsMenu", "Dungeon"));
        loaders = new HashMap<String, FXMLLoader>();
        for (String scene : scenes) {
            FXMLLoader loader =  new FXMLLoader(getClass().getResource(scene + "View.fxml"));
            loaders.put(scene, loader);
        }
        changeScene("MainMenu");
    }

    public Controller getController(String sceneName) {
        if (sceneName == "MainMenu") {
            return new MainMenuController(this);
        }
        else if (sceneName == "MapsMenu") {
            return new MapsMenuController(this);
        }
        return null;
    }

    public void changeScene(String sceneName) throws IOException {
        FXMLLoader loader = loaders.get(sceneName);
        if (loader == null) { return;}
        loader.setController(getController(sceneName));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
