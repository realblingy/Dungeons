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
        // stage.setResizable(false);
        stage.setTitle("Dungeon");
        dungeonMap = "maze";
        changeScene("MainMenu");
    }

    public Controller getController(String sceneName) {
        if (sceneName == "MainMenu") {
            return new MainMenuController(this);
        }
        else if (sceneName == "MapsMenu") {
            return new MapsMenuController(this);
        }
        else if (sceneName == "Dungeon") {
            try {
                DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(dungeonMap + ".json");
                return new DungeonController(dungeonLoader.load(), dungeonLoader.getEntities(), this);
            }
            catch (IOException e) {
                System.out.println("Cannot load Dungeon.");
            }
        }
        return null;
    }

    public void changeScene(String sceneName) throws IOException {
        FXMLLoader loader =  new FXMLLoader(getClass().getResource(sceneName + "View.fxml"));
        loader.setController(getController(sceneName));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.show();
    }

    public void update(Controller controller) throws IOException {
        if (controller instanceof MainMenuController) {
            MainMenuController c = (MainMenuController) controller;
            switch(c.getAction()) {
                case "play":
                    changeScene("Dungeon");
                    break;
                case "maps":
                    changeScene("MapsMenu");
                    break;
            }
        }
        else if (controller instanceof MapsMenuController) {
            MapsMenuController c = (MapsMenuController) controller;
            changeScene("MainMenu");
            dungeonMap = c.getDungeon();
            System.out.println(dungeonMap);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
