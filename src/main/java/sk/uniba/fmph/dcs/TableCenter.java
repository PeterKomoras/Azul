package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TableCenter extends TyleSource implements TableCenterInterface{

    /** STARTING_PLAYER=0, RED=1, GREEN=2, YELLOW=3, BLUE=4, BLACK=5 */
    private final HashMap<Integer, Tile> idxToTile;
    private final ArrayList<Tile> tiles;
    public TableCenter() {
        this.idxToTile = new HashMap<>();
        for (Tile t: Tile.values()) idxToTile.put(t.ordinal(), t);
        this.tiles = new ArrayList<>();
        tiles.add(Tile.STARTING_PLAYER);
    }
    @Override
    public Tile[] take(int idx) {
        ArrayList<Tile> result = new ArrayList<>();
        if (idx < 0 || 5 < idx ) return new Tile[0];
        if (tiles.contains(idxToTile.get(idx))) {
            Tile givenTile = idxToTile.get(idx);
            for (Tile t: tiles) {
                if (t.equals(givenTile)) result.add(givenTile);
            }
            tiles.removeIf(t -> t.equals(givenTile));
        }

        if (result.isEmpty()) return new Tile[0];
        if (tiles.contains(Tile.STARTING_PLAYER)) {
            result.add(Tile.STARTING_PLAYER);
            tiles.remove(Tile.STARTING_PLAYER);
        }

        return result.toArray(new Tile[result.size()]);
    }

    @Override
    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    @Override
    public void startNewRound() {
        tiles.clear();
        tiles.add(Tile.STARTING_PLAYER);
    }

    @Override
    public String state() {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append("Table center: ");
        for (final Tile tile : tiles) {
            toReturn.append(tile.toString());
        }
        return toReturn.toString();
    }

    @Override
    public void add(Tile[] newTiles) {
        tiles.addAll(new ArrayList<>(Arrays.asList(newTiles)));
    }

}
