package sk.uniba.fmph.dcs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
class FakeUsedTyles implements UsedTilesTakeAllInterface {
    @Override
    public Tile[] takeAll() {
        return new Tile[]{Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE};
    }
}

public class BagTest {
    private FakeUsedTyles usedTiles;

    private Bag bag;

    @BeforeEach
    public void setUp() {
        usedTiles = new FakeUsedTyles();
        Random r = new Random(0);
        bag  = new Bag(r, usedTiles, 4);
    }

    @Test
    public void test() {
        assertEquals("RRRRGGGGIIIIBBBBLLLL", bag.state());

        // generated pseudorandom ints 0 5 13 2 10 8 9 2 3 0 7 8 3 2 5 4 3 0 1 0
        // expected tiles RGBR BIBG GRLL IILL BRIG
        assertEquals(new Tile[]{Tile.RED, Tile.GREEN, Tile.BLUE, Tile.RED}, bag.take(4));
        assertEquals(new Tile[]{Tile.BLUE, Tile.YELLOW, Tile.BLUE, Tile.GREEN}, bag.take(4));
        assertEquals(new Tile[]{Tile.GREEN, Tile.RED, Tile.BLACK, Tile.BLACK}, bag.take(4));
        assertEquals(new Tile[]{Tile.YELLOW, Tile.YELLOW, Tile.BLACK, Tile.BLACK}, bag.take(4));
        assertEquals(new Tile[]{Tile.BLUE, Tile.RED, Tile.YELLOW, Tile.GREEN}, bag.take(4));
        assertEquals("", bag.state());
        assertEquals(new Tile[]{Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE}, bag.take(5));
        assertEquals("", bag.state());
        assertEquals(new Tile[]{Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE}, bag.take(4));
        assertEquals("B", bag.state());
        assertEquals(new Tile[]{Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE}, bag.take(4));
        assertEquals("BB", bag.state());
        assertEquals(new Tile[]{Tile.BLUE, Tile.BLUE}, bag.take(2));
        assertEquals("", bag.state());



    }
}
