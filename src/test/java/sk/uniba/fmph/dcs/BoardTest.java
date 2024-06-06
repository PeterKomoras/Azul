package sk.uniba.fmph.dcs;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Optional;

class FakePatternLine implements PatternLineInterface {
    public boolean putCalled = false;
    String state;
    FakePatternLine(String state){
        this.state = state;
    }
    public void put(Tile[] tiles) {
        putCalled = true;
    }
    public Points finishRound() {
        return new Points(5);
    }
    public String state() {
        return state;
    }
}
class FakeWallLine implements WallLineInterface{
    String state;
    FakeWallLine(String state){
        this.state = state;
    }
    public boolean canPutTile(Tile tile) {
        return false;
    }
    public Optional<Tile>[] getTiles() {
        return new Optional[0];
    }
    public Points putTile(Tile tile) {
        return null;
    }
    public String state() {
        return state;
    }
}
class FakeFloor implements FloorInterface{
    public void put(Collection<Tile> tiles) {
    }
    public String state() {
        return "floor";
    }
    public Points finishRound() {
        return new Points(2);
    }
}
public class BoardTest {
    private FakePatternLine[] patternLines = new FakePatternLine[2];
    private FakeWallLine[] wallLines = new FakeWallLine[2];
    private FakeFloor floor;
    private Board board;
    @Before
    public void setUp() {
        patternLines[0] = new FakePatternLine("[]");
        patternLines[1] = new FakePatternLine("[][]");
        wallLines[0] = new FakeWallLine("[1][2]");
        wallLines[1] = new FakeWallLine("[3][4]");
        floor = new FakeFloor();
        board = new Board(patternLines, wallLines, floor);
    }
    @Test
    public void test_put() {
        assertEquals("patternLine[0].put() should not be called.", false, patternLines[0].putCalled);
        board.put(0, new Tile[0]);
        assertEquals("patternLine[1].put() should not be called.", false, patternLines[1].putCalled);
        assertEquals("patternLine[0].put() should be called.", true, patternLines[0].putCalled);
    }
    @Test
    public void test_points() {
        assertEquals("Points should be 0.",0, board.getPoints().getValue());
        board.finishRound();
        assertEquals("Points should be 8.",8, board.getPoints().getValue());
        board.finishRound();
        assertEquals("Points should be 16.",16, board.getPoints().getValue());

    }
    @Test
    public void test_state() {
        assertEquals("board.state() test.", board.getPoints() + "\n" +
                "P1: []W1: [1][2]\n" +
                "P2: [][]W2: [3][4]\n" +
                "floor", board.state());
    }
}