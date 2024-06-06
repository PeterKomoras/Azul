package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TableAreaIntegrationTest {
    private TableArea tableArea;

    @BeforeEach
    public void setUp() {
        Random random = new Random(0);
        UsedTiles usedTiles = new UsedTiles();
        Bag bag = new Bag(random, usedTiles, 4);

        TableCenter tableCenter = new TableCenter();

        // tileSources: 5 factories + tableCenter
        ArrayList<TyleSource> tileSources = new ArrayList<>();
        for (int i = 0; i < 5; i++) tileSources.add(new Factory(bag, tableCenter));
        tileSources.add(tableCenter);

        tableArea = new TableArea(tileSources);
    }

    @Test
    public void test() {
        assertFalse(tableArea.isRoundEnd());
        assertEquals("Table:\n" +
                "RGBR\n" +
                "BIBG\n" +
                "GRLL\n" +
                "IILL\n" +
                "BRIG\n" +
                "Table center: S\n", tableArea.state());
        // incorrect sourceIdx
        assertEquals(new Tile[0], tableArea.take(-1, 1));
        assertEquals(new Tile[0], tableArea.take(6, 1));
        // take from the table center with STARTING_PLAYER tile only
        assertEquals(new Tile[0], tableArea.take(5, 1));
        assertEquals("Table:\n" +
                "RGBR\n" +
                "BIBG\n" +
                "GRLL\n" +
                "IILL\n" +
                "BRIG\n" +
                "Table center: S\n", tableArea.state());

        // correct takes

        assertEquals(new Tile[]{Tile.RED, Tile.RED}, tableArea.take(0, 1));
        assertFalse(tableArea.isRoundEnd());
        assertEquals("Table:\n" +
                "\n" +
                "BIBG\n" +
                "GRLL\n" +
                "IILL\n" +
                "BRIG\n" +
                "Table center: SGB\n", tableArea.state());

        assertEquals(new Tile[]{Tile.BLUE, Tile.BLUE}, tableArea.take(1, Tile.BLUE.ordinal()));
        assertFalse(tableArea.isRoundEnd());
        assertEquals("Table:\n" +
                "\n" +
                "\n" +
                "GRLL\n" +
                "IILL\n" +
                "BRIG\n" +
                "Table center: SGBIG\n", tableArea.state());

        assertEquals(new Tile[]{Tile.GREEN}, tableArea.take(2, Tile.GREEN.ordinal()));
        assertFalse(tableArea.isRoundEnd());
        assertEquals("Table:\n" +
                "\n" +
                "\n" +
                "\n" +
                "IILL\n" +
                "BRIG\n" +
                "Table center: SGBIGRLL\n", tableArea.state());

        assertEquals(new Tile[]{Tile.GREEN, Tile.GREEN, Tile.STARTING_PLAYER}, tableArea.take(5, Tile.GREEN.ordinal()));
        assertFalse(tableArea.isRoundEnd());
        assertEquals("Table:\n" +
                "\n" +
                "\n" +
                "\n" +
                "IILL\n" +
                "BRIG\n" +
                "Table center: BIRLL\n", tableArea.state());

        assertEquals(new Tile[]{Tile.BLUE}, tableArea.take(5, Tile.BLUE.ordinal()));
        assertFalse(tableArea.isRoundEnd());
        assertEquals("Table:\n" +
                "\n" +
                "\n" +
                "\n" +
                "IILL\n" +
                "BRIG\n" +
                "Table center: IRLL\n", tableArea.state());

        assertEquals(new Tile[]{Tile.YELLOW, Tile.YELLOW}, tableArea.take(3, Tile.YELLOW.ordinal()));
        assertFalse(tableArea.isRoundEnd());
        assertEquals("Table:\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "BRIG\n" +
                "Table center: IRLLLL\n", tableArea.state());

        assertEquals(new Tile[]{Tile.BLUE}, tableArea.take(4, Tile.BLUE.ordinal()));
        assertFalse(tableArea.isRoundEnd());
        assertEquals("Table:\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "Table center: IRLLLLRIG\n", tableArea.state());

        // Blue not in the center table
        assertEquals(new Tile[0], tableArea.take(5, Tile.BLUE.ordinal()));
        assertFalse(tableArea.isRoundEnd());
        assertEquals("Table:\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "Table center: IRLLLLRIG\n", tableArea.state());

        assertEquals(new Tile[]{Tile.BLACK,Tile.BLACK,Tile.BLACK,Tile.BLACK}, tableArea.take(5, Tile.BLACK.ordinal()));
        assertFalse(tableArea.isRoundEnd());
        assertEquals("Table:\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "Table center: IRRIG\n", tableArea.state());

        assertEquals(new Tile[]{Tile.RED, Tile.RED}, tableArea.take(5, Tile.RED.ordinal()));
        assertFalse(tableArea.isRoundEnd());
        assertEquals("Table:\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "Table center: IIG\n", tableArea.state());

        assertEquals(new Tile[]{Tile.YELLOW, Tile.YELLOW}, tableArea.take(5, Tile.YELLOW.ordinal()));
        assertFalse(tableArea.isRoundEnd());
        assertEquals("Table:\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "Table center: G\n", tableArea.state());

        // take from an empty factory

        assertEquals(new Tile[0], tableArea.take(1, Tile.RED.ordinal()));
        assertFalse(tableArea.isRoundEnd());
        assertEquals("Table:\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "Table center: G\n", tableArea.state());

        // last take

        assertEquals(new Tile[]{Tile.GREEN}, tableArea.take(5, Tile.GREEN.ordinal()));
        assert tableArea.isRoundEnd();
        assertEquals("Table:\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "Table center: \n", tableArea.state());

    }
}
