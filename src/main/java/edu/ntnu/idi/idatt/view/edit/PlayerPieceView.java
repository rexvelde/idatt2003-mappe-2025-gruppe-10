package edu.ntnu.idi.idatt.view.edit;

import edu.ntnu.idi.idatt.model.player.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * PlayerPieceView is a custom ImageView that represents a player's piece on the game board.
 * It displays the piece image based on the player's piece type.
 */
public class PlayerPieceView extends ImageView {
    private final Player player;

    /**
     * Constructor for PlayerPieceView.
     *
     * @param player The player whose piece is to be displayed.
     */
    public PlayerPieceView(Player player) {
        this.player = player;
        setFitWidth(45);
        setFitHeight(45);
        setPreserveRatio(true);

        setImage(new Image("/images/pieces/" + player.getPiece() + ".png", true));
    }

    /**
     * Returns the player associated with this PlayerPieceView.
     *
     * @return The player.
     */
    public Player getPlayer() {
        return player;
    }
}
