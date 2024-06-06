package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

class FakeTableArea implements TableAreaInterface {
    private final ArrayList<Tile> tiles;
    private final HashMap<Integer, Tile> idxToTile;

    FakeTableArea() {
        tiles = new ArrayList<>();
        tiles.addAll(Arrays.asList(Tile.values()));

        idxToTile = new HashMap<>();
        for (Tile t: Tile.values()) idxToTile.put(t.ordinal(), t);
    }

    @Override
    public Tile[] take(int sourceId, int idx) {
        if (sourceId  == 0 && tiles.contains(idxToTile.get(idx))) {
            tiles.remove(idxToTile.get(idx));
            if (tiles.contains(Tile.STARTING_PLAYER)) {
                tiles.remove(Tile.STARTING_PLAYER);
                return new Tile[]{idxToTile.get(idx), Tile.STARTING_PLAYER };
            }
            return new Tile[]{idxToTile.get(idx)};
        }
        return new Tile[0];
    }

    @Override
    public boolean isRoundEnd() {
        return tiles.isEmpty();
    }

    @Override
    public void startNewRound() {
        tiles.addAll(Arrays.asList(Tile.values()));
    }

    @Override
    public String state() {
        return null;
    }
}

class FakeGameObserver implements GameObserverInterface {

    @Override
    public void notifyEverybody(String state) {

    }

    @Override
    public void registerObserver(ObserverInterface observer) {

    }

    @Override
    public void cancelObserver(ObserverInterface observer) {

    }
}

class FakeBoard implements BoardInterface {

    @Override
    public void put(int destinationIdx, Tile[] tiles) {

    }

    @Override
    public FinishRoundResult finishRound() {
        return null;
    }

    @Override
    public void endGame() {

    }

    @Override
    public String state() {
        return null;
    }

    @Override
    public Points getPoints() {
        return null;
    }
}

public class GameTest {
    private Game game;


    @BeforeEach
    public void setUp() {
        ArrayList<BoardInterface> boards = new ArrayList<>();
        boards.add(new FakeBoard());
        boards.add(new FakeBoard());
        TableAreaInterface tableArea = new FakeTableArea();
        GameObserverInterface gameObserver = new FakeGameObserver();
        game = new Game(boards, tableArea, gameObserver);
    }

    @Test
    public void test() {
        assertEquals(0, game.getCurrentPlayerId());
        // wrong playerId
        for (int playerId = -10; playerId < 10; playerId++) if (playerId != 0 && playerId != 1) {
            assertFalse(game.take(playerId, 0, 1, 0));
            assertEquals(0, game.getCurrentPlayerId());
        }
        // wrong idx
        for (int idx = -10; idx < 10; idx++) if (idx < 1 || idx > 5) {
            assertFalse(game.take(0, 0, idx, 0));
            assertEquals(0, game.getCurrentPlayerId());
        }

        // correct takes
        assert game.take(0, 0, 1, 1);
        assertEquals(1, game.getCurrentPlayerId());
        assertFalse(game.take(0, 0, 2, 1));
        assertEquals(1, game.getCurrentPlayerId());
        assert game.take(1, 0, 2, 1);
        assertEquals(0, game.getCurrentPlayerId());

        // idx not in the table area
        assertFalse(game.take(0, 0, 2, 1));

        // correct takes
        assert game.take(0, 0, 3, 1);
        assert game.take(1, 0, 4, 1);
        assert game.take(0, 0, 5, 1);

        for (int i = 0; i < 100; i++) {
            // new round, starting player should be player 0
            assertFalse(game.take(1, 0, 1, 1));

            // correct takes
            assert game.take(0, 0, 1, 1);
            assert game.take(1, 0, 2, 1);
            assert game.take(0, 0, 3, 1);
            assert game.take(1, 0, 4, 1);
            assert game.take(0, 0, 5, 1);

        }
    }

}
