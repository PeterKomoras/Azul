package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collection;

import static java.lang.Math.min;

public final class Floor implements FloorInterface {
  private final UsedTilesGiveInterface usedTiles;
  private final ArrayList<Points> pointPattern;
  private ArrayList<Tile> tiles;

  public Floor(final UsedTilesGiveInterface usedTiles, final ArrayList<Points> pointPattern) {
    this.usedTiles = usedTiles;
    this.pointPattern = pointPattern;
    tiles = new ArrayList<Tile>();
  }

  public void put(final Collection<Tile> tiles) {
    this.tiles.addAll(tiles);
  }

  public String state() {
    StringBuilder state = new StringBuilder();
    state.append("Floor: ");
    for(int i = 0; i < min(pointPattern.size(), tiles.size()); i++){
      state.append("-" + pointPattern.get(i).getValue() + " " + tiles.get(i).toString() + " | ");
    }
    return state.toString();
  }

  public Points finishRound() {
    int sum = 0;
    for (int i = 0; i < tiles.size(); i++) {
      sum +=
          (i < pointPattern.size()
                  ? pointPattern.get(i)
                  : pointPattern.get(pointPattern.size() - 1))
              .getValue();
    }
    usedTiles.give(tiles);
    tiles = new ArrayList<Tile>();
    return new Points(sum);
  }
}
