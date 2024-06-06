package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FullColorRuleTest extends ScoreRulesTest {

    @Override
    protected void initializeScoringRule() {
        scoringRule = new FullColorRule();
    }

    @Test
    public void testCalculatePointsWithCompleteDiagonal1() {
        for (int i = 0; i < tiles.length; i++) {
            wall[i][i] = Optional.of(tiles[(i + 2) % tiles.length]);
        }

        int points = scoringRule.calculatePoints(wall);

        assertEquals(10, points);
    }

    @Test
    public void testCalculatePointsWithCompleteDiagonal2(){

        for (int i = 0; i < tiles.length; i++) {
            wall[i][i] = Optional.of(tiles[(i + 2) % tiles.length]);
            wall[i][(i+1) % tiles.length] = Optional.of(tiles[(i + 2) % tiles.length]);
        }

        int points = scoringRule.calculatePoints(wall);

        assertEquals(20, points);
    }

    @Test
    public void testCalculatePointsWithFullEachDiagonal(){

        for (int i = 0; i < tiles.length; i++) {
            wall[i][i] = Optional.of(tiles[(i + 2) % tiles.length]);
            wall[i][(i+1) % tiles.length] = Optional.of(tiles[(i + 2) % tiles.length]);
            wall[i][(i+2) % tiles.length] = Optional.of(tiles[(i + 2) % tiles.length]);
            wall[i][(i+3) % tiles.length] = Optional.of(tiles[(i + 2) % tiles.length]);
            wall[i][(i+4) % tiles.length] = Optional.of(tiles[(i + 2) % tiles.length]);
        }

        int points = scoringRule.calculatePoints(wall);

        assertEquals(50, points);
    }

    @Test
    public void testCalculatePointsWithNonCompleteDiagonal1() {

        wall[0][0] = Optional.of(tiles[0]);
        wall[1][1] = Optional.of(tiles[1]);

        int points = scoringRule.calculatePoints(wall);

        assertEquals(0, points);
    }

    @Test
    public void testCalculatePointsWithNonCompleteDiagonal2() {

        for(int i = 0; i < tiles.length; i+=2){
            wall[i][i] = Optional.of(tiles[(i + 2) % tiles.length]);
        }

        int points = scoringRule.calculatePoints(wall);

        assertEquals(0, points);
    }

    @Test
    public void testCalculateWithEmptyWall(){
        int points = scoringRule.calculatePoints(wall);

        assertEquals(0, points);
    }
}
