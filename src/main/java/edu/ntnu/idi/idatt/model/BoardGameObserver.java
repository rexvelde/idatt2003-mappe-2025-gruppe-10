package edu.ntnu.idi.idatt.model;

public interface BoardGameObserver {
    void onTurnChanged(Player player);
    void onPlayerMoved(Player player, int fromTileId, int toTileId);
    void onGameEnded(Player winner);
}
