package edu.ntnu.idi.idatt.view;

import edu.ntnu.idi.idatt.model.BoardGame;
import edu.ntnu.idi.idatt.model.BoardGameObserver;
import edu.ntnu.idi.idatt.model.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * View for the two dice and the roll button on the side of the board.
 */
public class DiceView extends VBox {

    private final ImageView diceNr1 = new ImageView();
    private final ImageView diceNr2 = new ImageView();
    private final Button rollButton = new Button("Roll");

    public DiceView(BoardGame game) {
        setAlignment(Pos.CENTER);
        setSpacing(10);
        diceNr1.setFitWidth(35);
        diceNr1.setFitHeight(35);
        diceNr2.setFitWidth(35);
        diceNr2.setFitHeight(35);
        getChildren().addAll(new HBox(10, diceNr1, diceNr2), rollButton);

        rollButton.setOnAction(e -> {
            rollButton.setDisable(true);
            game.playTurn();
        });

        game.addObserver(new BoardGameObserver() {
            @Override
            public void onTurnChanged(Player player) {
            }

            @Override
            public void onPlayerMoved(Player player, int from, int to) {
                diceNr1.setImage(loadFace(game.getDice().getDie(0)));
                diceNr2.setImage(loadFace(game.getDice().getDie(1)));
                rollButton.setDisable(false);
            }

            @Override
            public void onGameEnded(Player winner) {
                rollButton.setDisable(true);
            }
        });
    }

    private static Image loadFace(int value) {
        return new Image("/images/dice/" + value + ".png", true);
    }
}