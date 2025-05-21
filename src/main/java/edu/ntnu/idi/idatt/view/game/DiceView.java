package edu.ntnu.idi.idatt.view.game;

import edu.ntnu.idi.idatt.model.board.BoardGameObserver;
import edu.ntnu.idi.idatt.model.board.BoardGame;
import edu.ntnu.idi.idatt.model.player.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * View for the two dice and the roll button on the side of the board.
 */
public class DiceView extends VBox implements BoardGameObserver {

    private final ImageView diceNr1 = new ImageView();
    private final ImageView diceNr2 = new ImageView();
    private final HBox diceBox = new HBox(10, diceNr1, diceNr2);
    private final Button rollButton = new Button("Roll");
    private final Label plrNameLabel = new Label();

    public final BoardGame game;

    public DiceView(BoardGame game) {
        this.game = game;

        setAlignment(Pos.CENTER);
        setSpacing(10);
        getStyleClass().add("dice-view");

        diceNr1.setFitWidth(55);
        diceNr1.setFitHeight(55);
        diceNr2.setFitWidth(55);
        diceNr2.setFitHeight(55);
        diceBox.setAlignment(Pos.CENTER);

        plrNameLabel.getStyleClass().add("player-name-label");
        rollButton.getStyleClass().add("roll-button");

        getChildren().addAll(diceBox, plrNameLabel, rollButton);

        game.addObserver(this);
    }

    @Override
    public void onTurnChanged(Player player) {
        plrNameLabel.setText(player.getName() + "'s turn");
        rollButton.setDisable(false);
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

    private static Image loadFace(int value) {
        return new Image("/images/dice/" + value + ".png", true);
    }

    public Button getRollButton() {
        return rollButton;
    }
}