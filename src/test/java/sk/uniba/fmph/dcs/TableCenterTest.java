package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class TableCenterTest {
    private TableCenter tableCenter;

    @BeforeEach
    public void setUp() {
        tableCenter = new TableCenter();
        tableCenter.startNewRound();
    }

    @Test
    public void test_table_centre() {
        for (int i = 0; i < 100; i++) {
            assertFalse("There should be the STARTING_PLAYER tile", tableCenter.isEmpty());
            assertEquals("There should be the STARTING_PLAYER tile", "Table center: S", tableCenter.state());
            tableCenter.add(new Tile[]{Tile.RED, Tile.RED,});
            assertEquals("We expect SRR", "Table center: SRR", tableCenter.state());
            assertEquals("[RED, RED, STARTING_PLAYER]",
                    new Tile[]{Tile.RED, Tile.RED, Tile.STARTING_PLAYER,},
                    tableCenter.take(Tile.RED.ordinal()));
            assertTrue("Now the table center is empty", tableCenter.isEmpty());
            assertEquals("Now the table center is empty", "Table center: ", tableCenter.state());
            tableCenter.add(new Tile[]{Tile.RED, Tile.RED,});
            tableCenter.add(new Tile[]{Tile.BLUE, Tile.BLUE,});
            tableCenter.add(new Tile[]{Tile.BLACK});
            tableCenter.add(new Tile[]{Tile.RED, Tile.RED,});
            assertEquals("Table center: RRBBLRR", tableCenter.state());
            assertEquals(new Tile[]{Tile.BLACK}, tableCenter.take(Tile.BLACK.ordinal()));
            assertEquals("We expect RRBBRR", "Table center: RRBBRR", tableCenter.state());
            assertEquals(new Tile[]{Tile.RED, Tile.RED, Tile.RED, Tile.RED}, tableCenter.take(Tile.RED.ordinal()));
            assertEquals("We expect BB", "Table center: BB", tableCenter.state());

            tableCenter.startNewRound();
            assertEquals("We expect S", "Table center: S", tableCenter.state());
            assertEquals(new Tile[0], tableCenter.take(Tile.RED.ordinal()));

            tableCenter.startNewRound();
        }
    }

}