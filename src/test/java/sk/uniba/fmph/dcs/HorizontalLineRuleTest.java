package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class HorizontalLineRuleTest extends ScoreRulesTest {
    @Override
    protected void initializeScoringRule() {
        scoringRule = new HorizontalLineRule();
    }

    @Test
    public void testCalculatePointsWithCompleteHorizontalLine1() {
        wall[0][1] = Optional.of(tiles[0]);
        wall[0][4] = Optional.of(tiles[4]);
        for (int i = 0; i < tiles.length; i++) {
            wall[1][i] = Optional.of(tiles[(i + 1) % tiles.length]);
        }

        wall[2][0] = Optional.of(tiles[0]);
        wall[3][1] = Optional.of(tiles[1]);

        int points = scoringRule.calculatePoints(wall);

        assertEquals(2, points);
    }

    @Test
    public void testCalculatePointsWithCompleteHorizontalLine2() {

        for (int i = 0; i < tiles.length; i++) {
            wall[2][i] = Optional.of(tiles[(i + 2) % tiles.length]);
        }

        int points = scoringRule.calculatePoints(wall);

        assertEquals(2, points);
    }

    @Test
    public void testCalculatePointsWithAllCompleteHorizontalLine(){
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles.length; j++){
                wall[i][j] = Optional.of(tiles[(i + j) % tiles.length]);
            }
        }

        int points = scoringRule.calculatePoints(wall);

        assertEquals(10, points);
    }

    @Test
    public void testCalculatePointsWithNonCompleteHorizontalLine1() {

        wall[3][0] = Optional.of(tiles[0]);
        wall[3][2] = Optional.of(tiles[2]);

        int points = scoringRule.calculatePoints(wall);

        assertEquals(0, points);
    }

    @Test
    public void testCalculatePointsWithNonCompleteHorizontalLine2() {

        wall[0][1] = Optional.of(tiles[1]);
        wall[0][3] = Optional.of(tiles[3]);

        int points = scoringRule.calculatePoints(wall);

        assertEquals(0, points);
    }

    @Test
    public void testCalculatePointsWithEmptyWall() {

        int points = scoringRule.calculatePoints(wall);

        assertEquals(0, points);
    }

}
