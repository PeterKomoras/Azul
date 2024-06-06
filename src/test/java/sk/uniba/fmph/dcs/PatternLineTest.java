package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;

class FakeWallLine2 implements WallLineInterface {
    private final Set<Tile> tiles = new HashSet<>();


    @Override
    public boolean canPutTile(Tile tile) {
        return !tiles.contains(tile);
    }

    @Override
    public Optional<Tile>[] getTiles() {
        return new Optional[0];
    }

    /** Real points are tested in WallLineTest, this implementation of putTile is sufficient
     * for testing of PatternLine. */
    @Override
    public Points putTile(Tile tile) {
        if (canPutTile(tile)) {
            tiles.add(tile);
            return new Points(1);
        }
        return new Points(0);
    }

    @Override
    public String state() {
        return null;
    }
}

class











FakeFloor2 implements FloorInterface {

    @Override
    public void put(Collection<Tile> tiles) {

    }

    @Override
    public String state() {
        return null;
    }

    @Override
    public Points finishRound() {
        return null;
    }
}

public class PatternLineTest {
    private PatternLine[] patternLines;
    private FakeFloor2 floor;

    @BeforeEach
    public void setUp() {
        patternLines = new PatternLine[5];
        floor = new FakeFloor2();
        FakeUsedTiles2 usedTiles = new FakeUsedTiles2();
        for (int i = 0; i < 5; i++) {
            patternLines[i] = new PatternLine(i+1, floor, new FakeWallLine2(), usedTiles);
        }
    }

    @Test
    public void test() {
        for (PatternLine p : patternLines) {
            assertEquals("", p.state());
        }

        for (PatternLine p : patternLines) {
            p.put(new Tile[]{Tile.BLUE, Tile.BLUE, Tile.BLUE});
        }

        assertEquals("B", patternLines[0].state());
        assertEquals("BB", patternLines[1].state());
        assertEquals("BBB", patternLines[2].state());
        assertEquals("BBB", patternLines[3].state());
        assertEquals("BBB", patternLines[4].state());

        assertEquals(new Points(1), patternLines[0].finishRound());
        assertEquals(new Points(1), patternLines[1].finishRound());
        assertEquals(new Points(1), patternLines[2].finishRound());
        assertEquals(new Points(0), patternLines[3].finishRound());
        assertEquals(new Points(0), patternLines[4].finishRound());

        assertEquals("", patternLines[0].state());
        assertEquals("", patternLines[1].state());
        assertEquals("", patternLines[2].state());
        assertEquals("BBB", patternLines[3].state());
        assertEquals("BBB", patternLines[4].state());

        for (PatternLine p : patternLines) {
            p.put(new Tile[]{Tile.BLUE, Tile.BLUE, Tile.BLUE});
        }

        assertEquals("", patternLines[0].state());
        assertEquals("", patternLines[1].state());
        assertEquals("", patternLines[2].state());
        assertEquals("BBBB", patternLines[3].state());
        assertEquals("BBBBB", patternLines[4].state());

        assertEquals(new Points(0), patternLines[0].finishRound());
        assertEquals(new Points(0), patternLines[1].finishRound());
        assertEquals(new Points(0), patternLines[2].finishRound());
        assertEquals(new Points(1), patternLines[3].finishRound());
        assertEquals(new Points(1), patternLines[4].finishRound());

        for (PatternLine p : patternLines) {
            p.put(new Tile[]{Tile.RED, Tile.RED, Tile.RED});
        }

        assertEquals("R", patternLines[0].state());
        assertEquals("RR", patternLines[1].state());
        assertEquals("RRR", patternLines[2].state());
        assertEquals("RRR", patternLines[3].state());
        assertEquals("RRR", patternLines[4].state());

        assertEquals(new Points(1), patternLines[0].finishRound());
        assertEquals(new Points(1), patternLines[1].finishRound());
        assertEquals(new Points(1), patternLines[2].finishRound());
        assertEquals(new Points(0), patternLines[3].finishRound());
        assertEquals(new Points(0), patternLines[4].finishRound());

        for (PatternLine p : patternLines) {
            p.put(new Tile[]{Tile.GREEN, Tile.GREEN});
        }

        assertEquals("G", patternLines[0].state());
        assertEquals("GG", patternLines[1].state());
        assertEquals("GG", patternLines[2].state());
        assertEquals("RRR", patternLines[3].state());
        assertEquals("RRR", patternLines[4].state());

        assertEquals(new Points(1), patternLines[0].finishRound());
        assertEquals(new Points(1), patternLines[1].finishRound());
        assertEquals(new Points(0), patternLines[2].finishRound());
        assertEquals(new Points(0), patternLines[3].finishRound());
        assertEquals(new Points(0), patternLines[4].finishRound());

    }
}
