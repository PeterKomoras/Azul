package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameFinishedTest {
    private Optional<Tile>[][] wall;

    @BeforeEach
    public void setUp() {
        wall = new Optional[Tile.values().length - 1][Tile.values().length - 1];

        for (int i = 0; i < wall.length; i++) {
            for (int j = 0; j < wall[i].length; j++) {
                wall[i][j] = Optional.empty();
            }
        }
    }

    @Test
    public void testGameFinishedWithCompleteHorizontalLine() {
        for (int i = 0; i < Tile.values().length - 1; i++) {
            wall[0][i] = Optional.of(Tile.values()[i]);
        }

        FinishRoundResult result = GameFinished.gameFinished(wall);

        assertEquals(FinishRoundResult.GAME_FINISHED, result);
    }

    @Test
    public void testGameFinishedWithIncompleteHorizontalLine() {
        wall[0][0] = Optional.of(Tile.values()[0]);
        wall[0][1] = Optional.of(Tile.values()[1]);

        FinishRoundResult result = GameFinished.gameFinished(wall);
        assertEquals(FinishRoundResult.NORMAL, result);
    }

    @Test
    public void testGameFinishedWithEmptyWall() {
        FinishRoundResult result = GameFinished.gameFinished(wall);
        assertEquals(FinishRoundResult.NORMAL, result);
    }
}
