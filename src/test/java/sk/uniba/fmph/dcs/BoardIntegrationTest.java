package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;


class FakeUsedTiles2 implements UsedTilesGiveInterface {
    @Override
    public void give(Collection<Tile> ts) {

    }
}
public class BoardIntegrationTest {
    private Board board;
    private WallLine[] wallLines = new WallLine[5];
    private PatternLine[] patternLines = new PatternLine[5];

    private void setUpWallLines() {
        wallLines = new WallLine[5];
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
    }

    @BeforeEach
    public void setUp() {
        setUpWallLines();

        ArrayList<Points> pointPattern = new ArrayList<Points>();
        pointPattern.add(new Points(1));
        pointPattern.add(new Points(1));
        pointPattern.add(new Points(2));
        pointPattern.add(new Points(2));
        pointPattern.add(new Points(2));
        pointPattern.add(new Points(3));
        pointPattern.add(new Points(3));

        UsedTilesGiveInterface usedTiles = new FakeUsedTiles2();
        FloorInterface floor = new Floor(usedTiles, pointPattern);

        for (int i = 0; i < 5; i++) {
            patternLines[i] = new PatternLine(i+1, floor, wallLines[i], usedTiles);
        }

        board = new Board(patternLines, wallLines, floor);
    }

    @Test
    public void test() {
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(0, board.getPoints().getValue());
        assertEquals("Points [value=0]\n" +
                        "P1: W1: -----\n" +
                        "P2: W2: -----\n" +
                        "P3: W3: -----\n" +
                        "P4: W4: -----\n" +
                        "P5: W5: -----\n" +
                "Floor: " , board.state());

        board.endGame();
        assertEquals(0, board.getPoints().getValue());
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());

        for (Tile t: Tile.values()) {
            if (t.ordinal() > 0) board.put(t.ordinal()-1, new Tile[]{t,t,t});
        }
        /** STARTING_PLAYER=0, RED=1, GREEN=2, YELLOW=3, BLUE=4, BLACK=5 */

        assertEquals("Points [value=0]\n" +
                "P1: RW1: -----\n" +
                "P2: GGW2: -----\n" +
                "P3: IIIW3: -----\n" +
                "P4: BBBW4: -----\n" +
                "P5: LLLW5: -----\n" +
                "Floor: -1 R | -1 R | -2 G | " , board.state());

        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(0, board.getPoints().getValue());
        assertEquals("Points [value=0]\n" +
                "P1: W1: R----\n" +
                "P2: W2: --G--\n" +
                "P3: W3: ----I\n" +
                "P4: BBBW4: -----\n" +
                "P5: LLLW5: -----\n" +
                "Floor: " , board.state());

        board.put(0, new Tile[]{Tile.GREEN,Tile.GREEN,Tile.GREEN});
        board.put(2, new Tile[]{Tile.GREEN,Tile.GREEN,Tile.GREEN});

        assertEquals("Points [value=0]\n" +
                "P1: GW1: R----\n" +
                "P2: W2: --G--\n" +
                "P3: GGGW3: ----I\n" +
                "P4: BBBW4: -----\n" +
                "P5: LLLW5: -----\n" +
                "Floor: -1 G | -1 G | " , board.state());

        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals("Points [value=2]\n" +
                "P1: W1: RG---\n" +
                "P2: W2: --G--\n" +
                "P3: W3: ---GI\n" +
                "P4: BBBW4: -----\n" +
                "P5: LLLW5: -----\n" +
                "Floor: " , board.state());

        board.put(0, new Tile[]{Tile.YELLOW,Tile.YELLOW,Tile.YELLOW});
        board.put(1, new Tile[]{Tile.YELLOW,Tile.YELLOW,Tile.YELLOW});

        assertEquals("Points [value=2]\n" +
                "P1: IW1: RG---\n" +
                "P2: IIW2: --G--\n" +
                "P3: W3: ---GI\n" +
                "P4: BBBW4: -----\n" +
                "P5: LLLW5: -----\n" +
                "Floor: -1 I | -1 I | -2 I | " , board.state());


        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals("Points [value=7]\n" +
                "P1: W1: RGI--\n" +
                "P2: W2: --GI-\n" +
                "P3: W3: ---GI\n" +
                "P4: BBBW4: -----\n" +
                "P5: LLLW5: -----\n" +
                "Floor: " , board.state());

        board.put(0, new Tile[]{Tile.BLUE});
        board.put(3, new Tile[]{Tile.BLUE, Tile.BLUE, Tile.BLUE});
        assertEquals("Points [value=7]\n" +
                "P1: BW1: RGI--\n" +
                "P2: W2: --GI-\n" +
                "P3: W3: ---GI\n" +
                "P4: BBBBW4: -----\n" +
                "P5: LLLW5: -----\n" +
                "Floor: -1 B | -1 B | " , board.state());
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals("Points [value=13]\n" +
                "P1: W1: RGIB-\n" +
                "P2: W2: --GI-\n" +
                "P3: W3: ---GI\n" +
                "P4: W4: -B---\n" +
                "P5: LLLW5: -----\n" +
                "Floor: " , board.state());

        board.put(0, new Tile[]{Tile.BLACK});
        board.put(4, new Tile[]{Tile.BLACK, Tile.BLACK, Tile.BLACK});
        assertEquals("Points [value=13]\n" +
                "P1: LW1: RGIB-\n" +
                "P2: W2: --GI-\n" +
                "P3: W3: ---GI\n" +
                "P4: W4: -B---\n" +
                "P5: LLLLLW5: -----\n" +
                "Floor: -1 L | " , board.state());
        assertEquals(FinishRoundResult.GAME_FINISHED, board.finishRound());
        assertEquals("Points [value=18]\n" +
                "P1: W1: RGIBL\n" +
                "P2: W2: --GI-\n" +
                "P3: W3: ---GI\n" +
                "P4: W4: -B---\n" +
                "P5: W5: ---L-\n" +
                "Floor: " , board.state());

        board.endGame();

        assertEquals("Points [value=20]\n" +
                "P1: W1: RGIBL\n" +
                "P2: W2: --GI-\n" +
                "P3: W3: ---GI\n" +
                "P4: W4: -B---\n" +
                "P5: W5: ---L-\n" +
                "Floor: " , board.state());



    }
}
