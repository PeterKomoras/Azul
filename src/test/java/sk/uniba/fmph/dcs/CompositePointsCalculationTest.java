package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompositePointsCalculationTest extends ScoreRulesTest {

    @Override
    protected void initializeScoringRule() {
        scoringRule = new CompositePointsCalculation();
    }

    @Test
    public void testCalculatePointsWithHorizontalLine() {

        wall[0][1] = Optional.of(tiles[0]);
        wall[0][4] = Optional.of(tiles[4]);
        for (int i = 0; i < tiles.length; i++) {
            wall[1][i] = Optional.of(tiles[(i + 1) % tiles.length]);
        }
        wall[2][0] = Optional.of(tiles[0]);
        wall[3][1] = Optional.of(tiles[1]);

        ScoringRule horizontalLineRule = new HorizontalLineRule();
        ScoringRule verticalLineRule = new VerticalLineRule();
        ScoringRule fullColorRule = new FullColorRule();
        ((CompositePointsCalculation) scoringRule).addRule(fullColorRule);
        ((CompositePointsCalculation) scoringRule).addRule(verticalLineRule);
        ((CompositePointsCalculation) scoringRule).addRule(horizontalLineRule);

        int points = scoringRule.calculatePoints(wall);

        assertEquals(2, points);
    }

    @Test
    public void testCalculatePointsWithRowAndColumn() {

        wall[0][0] = Optional.of(tiles[2]);
        wall[0][1] = Optional.of(tiles[0]);
        wall[0][2] = Optional.of(tiles[1]);
        wall[0][3] = Optional.of(tiles[3]);
        wall[0][4] = Optional.of(tiles[4]);


        wall[1][1] = Optional.of(tiles[1]);
        wall[2][1] = Optional.of(tiles[2]);
        wall[3][1] = Optional.of(tiles[3]);
        wall[4][1] = Optional.of(tiles[4]);

        wall[2][2] = Optional.of(tiles[3]);

        ScoringRule horizontalLineRule = new HorizontalLineRule();
        ScoringRule verticalLineRule = new VerticalLineRule();
        ScoringRule fullColorRule = new FullColorRule();
        ((CompositePointsCalculation) scoringRule).addRule(fullColorRule);
        ((CompositePointsCalculation) scoringRule).addRule(verticalLineRule);
        ((CompositePointsCalculation) scoringRule).addRule(horizontalLineRule);

        int points = scoringRule.calculatePoints(wall);

        assertEquals(9, points);
    }

    @Test
    public void testCalculatePointsWithEmptyRuleList() {
        int points = scoringRule.calculatePoints(wall);

        assertEquals(0, points);
    }
}