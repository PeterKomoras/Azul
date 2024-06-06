package sk.uniba.fmph.dcs;

import java.util.Optional;

public interface WallLineInterface {
    boolean canPutTile(Tile tile);
    Optional<Tile>[] getTiles();
    Points putTile(Tile tile);
    String state();
}
