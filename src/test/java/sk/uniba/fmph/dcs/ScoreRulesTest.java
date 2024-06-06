package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.BeforeEach;

import java.util.Optional;

public abstract class ScoreRulesTest {
    protected ScoringRule scoringRule;
    protected Tile[] tiles;
    protected Optional<Tile>[][] wall;

    @BeforeEach
    public void setUp() {
        initializeScoringRule();
        initializeTiles();
        initializeWall();
    }

    protected abstract void initializeScoringRule();

    protected void initializeTiles() {
        tiles = new Tile[Tile.values().length - 1];
        int i = 0;
        for (Tile tile : Tile.values()) {
            if (tile != Tile.STARTING_PLAYER) {
                tiles[i] = tile;
                i++;
            }
        }
    }

    protected void initializeWall() {
        wall = new Optional[tiles.length][tiles.length];
        for(int j = 0; j < wall.length; j++){
            for(int k = 0; k < wall.length; k++){
                wall[j][k] = Optional.empty();
            }
        }
    }
}
