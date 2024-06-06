package sk.uniba.fmph.dcs;

import java.util.Optional;

public interface ScoringRule {
    int calculatePoints(Optional<Tile>[][] wall);
}
