package sk.uniba.fmph.dcs;

import java.util.*;

public class Bag  implements BagInterface {

    private final ArrayList<Tile> tiles;
    private final Random random;
    private final UsedTilesTakeAllInterface usedTiles;
    public Bag(Random random, UsedTilesTakeAllInterface usedTiles) {
        this.random = random;
        this.usedTiles = usedTiles;
        this.tiles = new ArrayList<>();
        for (Tile t: Tile.values()) {
            if (!t.equals(Tile.STARTING_PLAYER)) {
                for (int i = 0; i < 20; i++) tiles.add(t);
            }
        }
    }

    /** this constructor is for tests, it allows us to generate fewer tiles in the bag
     * what makes tests for used tiles easier.
      */
    public Bag(Random random, UsedTilesTakeAllInterface usedTiles, int numOfTiles) {
        this.random = random;
        this.usedTiles = usedTiles;
        this.tiles = new ArrayList<>();
        for (Tile t: Tile.values()) {
            if (!t.equals(Tile.STARTING_PLAYER)) {
                for (int i = 0; i < numOfTiles; i++) tiles.add(t);
            }
        }
    }

    @Override
    public Tile[] take(int count) {
        if (count > tiles.size() && tiles.size() >= 4) count = tiles.size();
        Tile[] toReturn = new Tile[count];
        for (int i = 0; i < count; i++) {
            if (tiles.isEmpty()) tiles.addAll(List.of(usedTiles.takeAll()));
            toReturn[i] = tiles.get(random.nextInt(tiles.size()));
            tiles.remove(toReturn[i]);
        }
        return toReturn;
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
