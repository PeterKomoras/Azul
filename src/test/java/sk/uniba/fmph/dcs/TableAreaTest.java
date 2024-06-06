package sk.uniba.fmph.dcs;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Collection;


public class TableAreaTest {


    @Test
    public void testTakeValidSource() {

        TableCenter tableCenter = new TableCenter();
        Tile[] tilesToAdd = new Tile[]{Tile.RED, Tile.RED, Tile.RED};
        tableCenter.add(tilesToAdd);
        TableArea tableArea = new TableArea(new ArrayList<>(Collections.singletonList(tableCenter)));

        Tile[] takenTiles = tableArea.take(0, Tile.RED.ordinal());
        tilesToAdd = ArrayUtils.add(tilesToAdd,Tile.STARTING_PLAYER);
        for (int i = 0; i < takenTiles.length; i++) {
            assertEquals(tilesToAdd[i], takenTiles[i]);
        }
        assertTrue(tableCenter.isEmpty());
    }



    @Test
    public void testTakeInvalidSource() {
        TableCenter tableCenter = new TableCenter();
        TableArea tableArea = new TableArea(new ArrayList<>(Collections.singletonList(tableCenter)));

        Tile[] takenTiles = tableArea.take(1, Tile.RED.ordinal());

        assertEquals(0, takenTiles.length);
    }



    @Test
    public void testIsRoundEndEmptySources() {
        TableCenter source1 = new TableCenter();
        TableCenter source2 = new TableCenter();
        ArrayList<TyleSource> sources = new ArrayList<>(Arrays.asList(source1, source2));
        TableArea tableArea = new TableArea(sources);

        assertFalse(tableArea.isRoundEnd());
        tableArea.take(0, Tile.STARTING_PLAYER.ordinal());
        tableArea.take(1, Tile.STARTING_PLAYER.ordinal());
        assertTrue(tableArea.isRoundEnd());
    }


    @Test
    public void testIsRoundEndNonEmptySources() {
        TableCenter tableCenter = new TableCenter();
        tableCenter.add(new Tile[]{Tile.RED});
        TableArea tableArea = new TableArea(new ArrayList<>(Collections.singletonList(tableCenter)));


        assertFalse(tableArea.isRoundEnd());
    }


    @Test
    public void testStartNewRound() {
        TableCenter source1 = new TableCenter();
        source1.add(new Tile[]{Tile.RED, Tile.RED, Tile.RED});
        TableCenter source2 = new TableCenter();
        ArrayList<TyleSource> sources = new ArrayList<>(Arrays.asList(source1, source2));
        TableArea tableArea = new TableArea(sources);

        tableArea.startNewRound();

        assertFalse(source1.isEmpty());
        assertFalse(source2.isEmpty());

        tableArea.take(0, Tile.STARTING_PLAYER.ordinal());
        tableArea.take(1, Tile.STARTING_PLAYER.ordinal());

        assertTrue(source1.isEmpty());
        assertTrue(source2.isEmpty());
    }

    @Test
    public void testState() {
        TableCenter source1 = new TableCenter();
        source1.add(new Tile[]{Tile.RED, Tile.BLACK, Tile.RED});
        TableCenter source2 = new TableCenter();
        source2.add(new Tile[]{Tile.GREEN, Tile.GREEN, Tile.GREEN});
        ArrayList<TyleSource> sources = new ArrayList<>(Arrays.asList(source1, source2));
        TableArea tableArea = new TableArea(sources);

        String expectedState = "Table:\n" + source1.state() + "\n" + source2.state() + "\n";

        assertEquals(expectedState, tableArea.state());
    }
}
