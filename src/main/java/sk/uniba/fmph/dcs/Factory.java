package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Factory extends TyleSource {
    /** STARTING_PLAYER=0, RED=1, GREEN=2, YELLOW=3, BLUE=4, BLACK=5 */
    private final HashMap<Integer, Tile> idxToTile;
    private final ArrayList<Tile> tiles = new ArrayList<>();
    private final BagInterface bag;
    private final TableCenterInterface tableCenter;

    public Factory(BagInterface bag, TableCenterInterface tableCenter) {
        this.bag = bag;
        this.idxToTile = new HashMap<>();
        for (Tile t: Tile.values()) idxToTile.put(t.ordinal(), t);
        this.tiles.addAll(List.of(bag.take(4)));
        this.tableCenter = tableCenter;
    }

    @Override
    public Tile[] take(int idx) {
        ArrayList<Tile> toReturn = new ArrayList<>();
        if (idx < 0 || 5 < idx ) return new Tile[0];
        if (tiles.contains(idxToTile.get(idx))) {
            Tile givenTile = idxToTile.get(idx);
            for (Tile t: tiles) {
                if (t.equals(givenTile)) toReturn.add(givenTile);
            }
            tiles.removeIf(t -> t.equals(givenTile));
        }
        tableCenter.add(tiles.toArray(new Tile[0]));
        tiles.clear();

        return toReturn.toArray(new Tile[0]);
    }

    @Override
    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    @Override
    public void startNewRound() {
        tiles.clear();
        tiles.addAll(List.of(bag.take(4)));
    }

    @Override
    public String state() {
        StringBuilder toReturn = new StringBuilder();
        for (final Tile tile : tiles) {
            toReturn.append(tile.toString());
        }
        return toReturn.toString();
    }
}
