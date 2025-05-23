package edu.ntnu.idi.idatt.view.edit;

import edu.ntnu.idi.idatt.model.player.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class PlayerPieceView extends ImageView {
    private final Player player;

    public PlayerPieceView(Player player) {
        this.player = player;
        setFitWidth(45);
        setFitHeight(45);
        setPreserveRatio(true);

        setImage(new Image("/images/pieces/" + player.getPiece() + ".png", true));
    }

    public Player getPlayer() {
        return player;
    }
}
