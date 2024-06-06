package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class GameIntegrationTest {
    private Game game;
    private TableArea tableArea;
    private UsedTiles usedTiles = new UsedTiles();
    private ArrayList<BoardInterface> boards;

    private Floor createFloor () {
        ArrayList<Points> pointPattern = new ArrayList<Points>();
        pointPattern.add(new Points(1));
        pointPattern.add(new Points(1));
        pointPattern.add(new Points(2));
        pointPattern.add(new Points(2));
        pointPattern.add(new Points(2));
        pointPattern.add(new Points(3));
        pointPattern.add(new Points(3));

        return new Floor(usedTiles, pointPattern);

    }
    public void setTableArea() {
        Random random = new Random(0);
        Bag bag = new Bag(random, usedTiles, 20);

        TableCenter tableCenter = new TableCenter();

        // tileSources: 2 factories + tableCenter
        ArrayList<TyleSource> tileSources = new ArrayList<>();
        for (int i = 0; i < 2; i++) tileSources.add(new Factory(bag, tableCenter));
        tileSources.add(tableCenter);

        tableArea = new TableArea(tileSources);
    }

    private WallLine[] setUpWallLines() {
        WallLine[] wallLines = new WallLine[5];
        for (int i = 0; i < 5; i++){

            Tile[] tiles = new Tile[5];
            int j = i;
            for (Tile tile : Tile.values()){
                if (tile != Tile.STARTING_PLAYER){
                    tiles[j] = tile;
                    j++;
                    if (j >= 5)
                        j = 0;
                }
            }
            WallLine lineUp = null;
            WallLine lineDown = null;
            wallLines[i] = new WallLine(tiles, lineUp, lineDown);
        }
        for (int i = 0; i < 5; i++){
            if(i == 0){
                wallLines[i].setUp(null);
                wallLines[i].setDown(wallLines[i+1]);
            }
            else if(i == 4) {
                wallLines[i].setDown(null);
                wallLines[i].setUp(wallLines[i-1]);
            }
            else{
                wallLines[i].setUp(wallLines[i-1]);
                wallLines[i].setDown(wallLines[i+1]);
            }
        }
        return wallLines;
    }

    private Board createBoard() {
        Floor floor = createFloor();
        WallLine[] wallLines = setUpWallLines();
        PatternLine[] patternLines = new PatternLine[5];

        for (int i = 0; i < 5; i++) {
            patternLines[i] = new PatternLine(i+1, floor, wallLines[i], usedTiles);
        }

        return new Board(patternLines, wallLines, floor);
    }


    @BeforeEach
    public void setUp() {
        setTableArea();
        boards = new ArrayList<>();
        boards.add(createBoard());
        boards.add(createBoard());

        game = new Game(boards, tableArea, new GameObserver());
    }

    @Test
    public void test() {
        BoardInterface b0 = boards.get(0);
        BoardInterface b1 = boards.get(1);
        assertNotNull(game.getGameObserver());

        assertFalse(game.isGameOver());
        assertFalse(tableArea.isRoundEnd());
        assertEquals("Table:\n" +
                "BBGL\n" +
                "BGBG\n" +
                "Table center: S\n", tableArea.state());

        assertEquals(FinishRoundResult.NORMAL, b0.finishRound());
        assertEquals(0, b0.getPoints().getValue());
        assertEquals("Points [value=0]\n" +
                "P1: W1: -----\n" +
                "P2: W2: -----\n" +
                "P3: W3: -----\n" +
                "P4: W4: -----\n" +
                "P5: W5: -----\n" +
                "Floor: " , b0.state());

        assertEquals(FinishRoundResult.NORMAL, b1.finishRound());
        assertEquals(0, b1.getPoints().getValue());
        assertEquals("Points [value=0]\n" +
                "P1: W1: -----\n" +
                "P2: W2: -----\n" +
                "P3: W3: -----\n" +
                "P4: W4: -----\n" +
                "P5: W5: -----\n" +
                "Floor: " , b1.state());

        // player0 starts game
        game.take(0, 0, Tile.GREEN.ordinal(), 0);
        game.take(1, 1, Tile.BLUE.ordinal(), 1);
        game.take(0, 2, Tile.BLUE.ordinal(), 1);
        game.take(1, 2, Tile.BLACK.ordinal(), 0);
        game.take(0, 2, Tile.GREEN.ordinal(), 2);

        assertEquals("Points [value=1]\n" +
                "P1: W1: -G---\n" +
                "P2: W2: ----B\n" +
                "P3: GGW3: -----\n" +
                "P4: W4: -----\n" +
                "P5: W5: -----\n" +
                "Floor: " , b0.state());
        assertEquals("Points [value=3]\n" +
                "P1: W1: ----L\n" +
                "P2: W2: ----B\n" +
                "P3: W3: -----\n" +
                "P4: W4: -----\n" +
                "P5: W5: -----\n" +
                "Floor: " , b1.state());

        game.take(0, 0, Tile.RED.ordinal(), 0);
        game.take(1, 1, Tile.RED.ordinal(), 1);
        game.take(0, 2, Tile.BLACK.ordinal(), 1);
        game.take(1, 2, Tile.YELLOW.ordinal(), 0);
        game.take(0, 2, Tile.BLUE.ordinal(), 2);
        assertEquals("Points [value=3]\n" +
                "P1: W1: RG---\n" +
                "P2: W2: L---B\n" +
                "P3: GGW3: -----\n" +
                "P4: W4: -----\n" +
                "P5: W5: -----\n" +
                "Floor: " , b0.state());
        assertEquals("Points [value=4]\n" +
                "P1: W1: --I-L\n" +
                "P2: W2: -R--B\n" +
                "P3: W3: -----\n" +
                "P4: W4: -----\n" +
                "P5: W5: -----\n" +
                "Floor: " , b1.state());

        game.take(0, 0, Tile.BLUE.ordinal(), 0);
        game.take(1, 1, Tile.RED.ordinal(), 0);
        game.take(0, 2, Tile.GREEN.ordinal(), 1);
        game.take(1, 2, Tile.YELLOW.ordinal(), 1);
        game.take(0, 2, Tile.BLACK.ordinal(), 3);
        game.take(1, 2, Tile.BLUE.ordinal(), 2);

        assertEquals("Points [value=3]\n" +
                "P1: W1: RG-B-\n" +
                "P2: W2: L-G-B\n" +
                "P3: GGW3: -----\n" +
                "P4: LW4: -----\n" +
                "P5: W5: -----\n" +
                "Floor: " , b0.state());
        assertEquals("Points [value=5]\n" +
                "P1: W1: R-I-L\n" +
                "P2: IW2: -R--B\n" +
                "P3: BW3: -----\n" +
                "P4: W4: -----\n" +
                "P5: W5: -----\n" +
                "Floor: " , b1.state());

        game.take(0, 0, Tile.BLACK.ordinal(), 0);
        game.take(1, 1, Tile.GREEN.ordinal(), 0);
        game.take(0, 2, Tile.YELLOW.ordinal(), 1);
        game.take(1, 2, Tile.BLUE.ordinal(), 2);
        game.take(0, 2, Tile.RED.ordinal(), 4);
        assertEquals("Points [value=11]\n" +
                "P1: W1: RG-BL\n" +
                "P2: W2: L-GIB\n" +
                "P3: GGW3: -----\n" +
                "P4: LW4: -----\n" +
                "P5: RW5: -----\n" +
                "Floor: " , b0.state());
        assertEquals("Points [value=10]\n" +
                "P1: W1: RGI-L\n" +
                "P2: IW2: -R--B\n" +
                "P3: W3: B----\n" +
                "P4: W4: -----\n" +
                "P5: W5: -----\n" +
                "Floor: " , b1.state());

        game.take(0, 0, Tile.YELLOW.ordinal(), 0);
        game.take(1, 1, Tile.YELLOW.ordinal(), 1);
        game.take(0, 2, Tile.RED.ordinal(), 1);
        game.take(1, 2, Tile.BLACK.ordinal(), 2);
        game.take(0, 2, Tile.GREEN.ordinal(), 2);
        game.take(1, 2, Tile.BLUE.ordinal(), 0);
        assertEquals("Points [value=22]\n" +
                "P1: W1: RGIBL\n" +
                "P2: RW2: L-GIB\n" +
                "P3: W3: ---G-\n" +
                "P4: LW4: -----\n" +
                "P5: RW5: -----\n" +
                "Floor: " , b0.state());
        assertEquals("Points [value=26]\n" +
                "P1: W1: RGIBL\n" +
                "P2: W2: -R-IB\n" +
                "P3: W3: BL---\n" +
                "P4: W4: -----\n" +
                "P5: W5: -----\n" +
                "Floor: " , b1.state());

        assert game.isGameOver();
    }
}
