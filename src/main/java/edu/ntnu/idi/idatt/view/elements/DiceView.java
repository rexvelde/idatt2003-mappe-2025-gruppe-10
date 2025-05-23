package edu.ntnu.idi.idatt.view.elements;

import edu.ntnu.idi.idatt.logger.LoggerToFile;
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

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * View for the two dice and the roll button on the side of the board.
 */
public class DiceView extends VBox implements BoardGameObserver {
    private ArrayList<ImageView> diceList = new ArrayList<>();

    private HBox diceBox = new HBox(10);
    private final Button rollButton = new Button("Roll");
    private final Label plrNameLabel = new Label();
    private boolean diceRolled = false;

    public final BoardGame game;

    public DiceView(BoardGame game, int diceAmount) {
        this.game = game;

        setAlignment(Pos.CENTER);
        setSpacing(10);
        getStyleClass().add("dice-view");

        for (int i = 0; i < diceAmount; i++) {
            ImageView die = new ImageView();

            die.setFitWidth(55);
            die.setFitHeight(55);

            diceList.add(die);
            diceBox.getChildren().add(die);
        }

        diceBox.setAlignment(Pos.CENTER);

        plrNameLabel.getStyleClass().add("player-name-label");
        rollButton.getStyleClass().add("roll-button");

        getChildren().addAll(diceBox, plrNameLabel, rollButton);

        game.addObserver(this);
        LoggerToFile.log(Level.INFO, "Dice view have loaded", getClass());
    }

    @Override
    public void onTurnChanged(Player player) {
        diceRolled = false;
        plrNameLabel.setText(player.getName() + "'s turn");
        rollButton.setDisable(false);
    }

    @Override
    public void onPlayerMoved(Player player, int from, int to) {
        if (!diceRolled) {
            for (int i = 0; i < diceList.size(); i++) {
                diceList.get(i).setImage(loadFace(game.getDice().getDie(i)));
            }
            diceRolled = true;
        }
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