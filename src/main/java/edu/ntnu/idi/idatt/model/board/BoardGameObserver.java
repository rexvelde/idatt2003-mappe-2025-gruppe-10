package edu.ntnu.idi.idatt.model.board;

import edu.ntnu.idi.idatt.model.player.Player;

/**
 * The BoardGameObserver interface is used to notify observers about changes in the game state.
 * It includes methods for notifying when a player's turn changes, when a player moves, and when the game ends.
 */
public interface BoardGameObserver {
    void onTurnChanged(Player player);

    void onPlayerMoved(Player player, int fromTileId, int toTileId);

    void onGameEnded(Player winner);
}
