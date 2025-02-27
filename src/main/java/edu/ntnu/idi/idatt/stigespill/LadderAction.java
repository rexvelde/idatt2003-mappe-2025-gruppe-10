package edu.ntnu.idi.idatt.stigespill;

import edu.ntnu.idi.idatt.model.Player;
import edu.ntnu.idi.idatt.model.TileAction;

public class LadderAction implements TileAction {
  private int destination;

  public LadderAction(int destination) {
    this.destination = destination;
  }

  public void perform(Player player) {
    // TODO: Perform some action on player
  };
}
