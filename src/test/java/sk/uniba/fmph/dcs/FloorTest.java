package sk.uniba.fmph.dcs;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

class FakeUsedTiles implements UsedTilesGiveInterface {
  public ArrayList<Tile> tiles;

  public FakeUsedTiles() {
    tiles = new ArrayList<Tile>();
  }

  public void give(Collection<Tile> t) {
    tiles.addAll(t);
  }
}

public class FloorTest {
  private FakeUsedTiles usedTiles;
  private Floor floor;

  @Before
  public void setUp() {
    usedTiles = new FakeUsedTiles();
    ArrayList<Points> pointPattern = new ArrayList<Points>();
    pointPattern.add(new Points(1));
    pointPattern.add(new Points(1));
    pointPattern.add(new Points(2));
    pointPattern.add(new Points(2));
    floor = new Floor(usedTiles, pointPattern);
  }

  @Test
  public void test_tiles() {
    ArrayList<Tile> tiles = new ArrayList<Tile>();
    tiles.add(Tile.STARTING_PLAYER);
    tiles.add(Tile.RED);
    tiles.add(Tile.GREEN);
    tiles.add(Tile.RED);
    assertEquals("Floor should be empty when created.", "Floor: ", floor.state());
    floor.put(tiles);
    assertEquals("Floor should contain tiles we put on it.", "Floor: -1 S | -1 R | -2 G | -2 R | ", floor.state());
    Points points = floor.finishRound();
    assertEquals("Floor should be empty after the round is finished.", "Floor: ", floor.state());
    assertEquals(
        "Incorrect points calculation when there are more tiles than pattern size",
        6,
        points.getValue());
    assertArrayEquals(
        "Used tiles should get the tiles", tiles.toArray(), usedTiles.tiles.toArray());

    floor.put(Arrays.asList(Tile.RED));
    floor.put(Arrays.asList(Tile.GREEN));
    floor.put(new ArrayList<Tile>());
    assertEquals("Floor should contain tiles we put on it.", "Floor: -1 R | -1 G | ", floor.state());
    Points points2 = floor.finishRound();
    assertEquals("Floor should be empty after the round is finished.", "Floor: ", floor.state());
    assertEquals(
        "Incorrect points calculation when there are less tiles than pattern size",
        2,
        points2.getValue());
    tiles.add(Tile.RED);
    tiles.add(Tile.GREEN);
    assertArrayEquals(
        "Used tiles should get the tiles", tiles.toArray(), usedTiles.tiles.toArray());
  }
}
