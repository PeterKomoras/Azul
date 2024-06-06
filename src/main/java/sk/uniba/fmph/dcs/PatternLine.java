package sk.uniba.fmph.dcs;

import java.util.*;

public class PatternLine implements PatternLineInterface {
    public int capacity;
    private final FloorInterface floor;
    private final WallLineInterface wallLine;
    private final ArrayList<Tile> tilesOnLine = new ArrayList<>();
    private final UsedTilesGiveInterface usedTiles;
    public PatternLine (int capacity, FloorInterface floor, WallLineInterface wallLine, UsedTilesGiveInterface usedTiles) {
        this.capacity = capacity;
        this.floor = floor;
        this.wallLine = wallLine;
        this.usedTiles = usedTiles;
    }

    @Override
    public void put(Tile[] tiles) {
        if (tiles.length > 0) {
            if (!wallLine.canPutTile(tiles[0])) floor.put(List.of(tiles));
            else if (!tilesOnLine.isEmpty() && tilesOnLine.get(0) != tiles[0]) floor.put(List.of(tiles));
            else {
                for (Tile t : tiles) {
                    if (t.equals(Tile.STARTING_PLAYER)) floor.put(Collections.singleton(t));
                    else if (tilesOnLine.size() < capacity) tilesOnLine.add(t);
                    else floor.put(Collections.singleton(t));
                }
            }
        }
    }

    @Override
    public Points finishRound() {
        if (capacity == tilesOnLine.size()) {
            Tile t = tilesOnLine.remove(0);
            usedTiles.give(tilesOnLine);

            tilesOnLine.clear();
            return wallLine.putTile(t);
        }
        return new Points(0);
    }

    @Override
    public String state() {
        StringBuilder toReturn = new StringBuilder();
        for (final Tile tile : tilesOnLine) {
            toReturn.append(tile.toString());
        }
        return toReturn.toString();
    }
}
