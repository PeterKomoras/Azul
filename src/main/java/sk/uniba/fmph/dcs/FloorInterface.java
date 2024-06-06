package sk.uniba.fmph.dcs;

import java.util.Collection;

public interface FloorInterface {
    void put(final Collection<Tile> tiles);
    String state();
    Points finishRound();
}
