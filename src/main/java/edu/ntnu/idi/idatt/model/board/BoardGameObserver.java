package edu.ntnu.idi.idatt.model.board;

import edu.ntnu.idi.idatt.model.player.Player;

public interface BoardGameObserver {
    void onTurnChanged(Player player);

    void onPlayerMoved(Player player, int fromTileId, int toTileId);

    void onGameEnded(Player winner);
}
