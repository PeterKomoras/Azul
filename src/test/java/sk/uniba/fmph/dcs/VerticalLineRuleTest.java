package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VerticalLineRuleTest extends ScoreRulesTest {

    @Override
    protected void initializeScoringRule() {
        scoringRule = new VerticalLineRule();
    }

    @Test
    public void testCalculatePointsWithCompleteVerticalLine1() {

        wall[0][1] = Optional.of(tiles[0]);
        wall[1][1] = Optional.of(tiles[1]);
        wall[2][1] = Optional.of(tiles[2]);
        wall[3][1] = Optional.of(tiles[3]);
        wall[4][1] = Optional.of(tiles[4]);

        wall[0][2] = Optional.of(tiles[0]);
        wall[2][2] = Optional.of(tiles[1]);

        int points = scoringRule.calculatePoints(wall);

        assertEquals(7, points);
    }

    @Test
    public void testCalculatePointsWithCompleteVerticalLine2() {

        for (int i = 0; i < tiles.length; i++) {
            wall[i][3] = Optional.of(tiles[(i + 2) % tiles.length]);
        }

        wall[0][2] = Optional.of(tiles[0]);
        wall[4][4] = Optional.of(tiles[1]);

        int points = scoringRule.calculatePoints(wall);

        // Assert that the points are as expected
        assertEquals(7, points);
    }

    @Test
    public void testCalculatePointsWithAllCompleteVerticalLine() {

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                wall[j][i] = Optional.of(tiles[(i + j) % tiles.length]);
            }
        }

        int points = scoringRule.calculatePoints(wall);

        assertEquals(35, points);
    }

    @Test
    public void testCalculatePointsWithNonCompleteVerticalLine1() {

        wall[0][2] = Optional.of(tiles[0]);
        wall[2][2] = Optional.of(tiles[2]);

        int points = scoringRule.calculatePoints(wall);

        assertEquals(0, points);
    }

    @Test
    public void testCalculatePointsWithNonCompleteVerticalLine2() {

        wall[1][0] = Optional.of(tiles[1]);
        wall[3][0] = Optional.of(tiles[3]);

        int points = scoringRule.calculatePoints(wall);

        assertEquals(0, points);
    }

    @Test
    public void testCalculatePointsWithEmptyWall() {
        int points = scoringRule.calculatePoints(wall);

        assertEquals(0, points);
    }
}
