package sk.uniba.fmph.dcs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

class FakeBag implements BagInterface {
    private final ArrayList<Tile> tiles = new ArrayList<>();
    private int id = 0;

    FakeBag() {
        // tiles RRRR RRBB BGLI
        for (int i = 0; i < 6; i++) {
            tiles.add(Tile.RED);
        }
        for (int i = 0; i < 3; i++) {
            tiles.add(Tile.BLUE);
        }
        tiles.add(Tile.GREEN);
        tiles.add(Tile.BLACK);
        tiles.add(Tile.YELLOW);

    }
    @Override
    public Tile[] take(int count) {
        Tile[] toReturn = new Tile[count];
        for (int i = 0; i < count; i++) {
            toReturn[i] = tiles.get(id);
            id++;
            if (id == tiles.size()) id = 0;
        }
        return toReturn;
    }

    @Override
    public String state() {
        return null;
    }
}

class FakeTableCenter implements TableCenterInterface {

    @Override
    public void add(Tile[] newTiles) {

    }
}



public class FactoryTest {
    private Factory factory;

    @BeforeEach
    public void setUp() {
        FakeBag bag = new FakeBag();
        FakeTableCenter tableCenter = new FakeTableCenter();
        factory = new Factory(bag, tableCenter);
    }

    @Test
    public void test_factory() {
        assertEquals( "RRRR", factory.state() );
        assertFalse(factory.isEmpty());
        // invalid idx
        for (int i = -10; i < 10; i++) {
            if (i == 0) i = 6;
            assertEquals(new Tile[0] ,factory.take(i));
        }
        assertEquals(new Tile[]{Tile.RED,Tile.RED,Tile.RED,Tile.RED}, factory.take(Tile.RED.ordinal()));
        assert factory.isEmpty() ;
        for (int i = -10; i < 10; i++) {
            assertEquals(new Tile[0] ,factory.take(i));
        }
        assertEquals("", factory.state());

        factory.startNewRound();
        assertEquals("RRBB", factory.state());
        assertFalse(factory.isEmpty());
        assertEquals(new Tile[]{Tile.BLUE, Tile.BLUE}, factory.take(Tile.BLUE.ordinal()));
        assert factory.isEmpty() ;
        assertEquals("", factory.state());
        for (int i = -10; i < 10; i++) { assertEquals(new Tile[0] ,factory.take(i)); }

        factory.startNewRound();
        assertEquals("BGLI", factory.state());
        assertFalse(factory.isEmpty());
        assertEquals(new Tile[]{Tile.YELLOW}, factory.take(Tile.YELLOW.ordinal()));
        assert factory.isEmpty() ;

        factory.startNewRound();
        assertEquals("RRRR", factory.state());
        assertEquals(new Tile[0], factory.take(Tile.STARTING_PLAYER.ordinal()));
        assertEquals(new Tile[0], factory.take(Tile.RED.ordinal()));
        assertEquals(new Tile[0], factory.take(Tile.BLUE.ordinal()));
        assert factory.isEmpty();

        factory.startNewRound();
        assertEquals("RRBB", factory.state());
        assertEquals(new Tile[0], factory.take(Tile.BLACK.ordinal()));
        assert factory.isEmpty();
    }

}
