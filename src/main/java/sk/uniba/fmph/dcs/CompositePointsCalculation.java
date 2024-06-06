package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompositePointsCalculation implements ScoringRule {
    private List<ScoringRule> rules = new ArrayList<>();

    public void addRule(ScoringRule rule) {
        rules.add(rule);
    }

    @Override
    public int calculatePoints(Optional<Tile>[][] wall) {
        int totalPoints = 0;
        for (ScoringRule rule : rules) {
            totalPoints += rule.calculatePoints(wall);
        }
        return totalPoints;
    }
}
