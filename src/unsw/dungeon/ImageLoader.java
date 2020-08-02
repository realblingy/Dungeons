package unsw.dungeon;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageLoader {

    private Item item;
    private Image image;

    public void setItem(Item item) {
        this.item = item;
    }

    public ImageView generateImageView() {
        if (item instanceof Potion) {
            image = new Image((new File("images/brilliant_blue_new.png")).toURI().toString());
        }
        else if (item instanceof Treasure) {
            image = new Image((new File("images/gold_pile.png")).toURI().toString());
        }
        else if (item instanceof DungeonKey) {
            image = new Image((new File("images/key.png")).toURI().toString());
        }
        else if (item instanceof Sword) {
            image = new Image((new File("images/greatsword_1_new.png")).toURI().toString());
        }

        return new ImageView(image);
    }

}