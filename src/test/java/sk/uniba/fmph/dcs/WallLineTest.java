package sk.uniba.fmph.dcs;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class WallLineTest {
    WallLine[] wallLines;
    @Before
    public void setUp() {
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

    @Test
    public void test_wallLines(){
        for (int i = 0; i < 5; i++) {
            assertEquals("Floor should be empty when created.", "-----", wallLines[i].state());
            assertEquals("The output of canPut should be true", true, wallLines[i].canPutTile(Tile.BLACK));
        }
        assertEquals("Method should add 1 point", 1, wallLines[2].putTile(Tile.BLACK).getValue());
        assertEquals("The output of canPut should be false", false, wallLines[2].canPutTile(Tile.BLACK));
        assertEquals("WallLine state() test", "-L---", wallLines[2].state());
        assertEquals("Method should add 2 points", 2, wallLines[1].putTile(Tile.RED).getValue());
        assertEquals("WallLine state() test", "-R---", wallLines[1].state());
        wallLines[0].putTile(Tile.BLACK);
        assertEquals("Method should add 1 point", 1, wallLines[0].putTile(Tile.RED).getValue());
        assertEquals("R---L", wallLines[0].state());
        assertEquals("Method should add 5 points", 5, wallLines[0].putTile(Tile.GREEN).getValue());
        assertEquals("Method should add 2 points", 2, wallLines[0].putTile(Tile.BLUE).getValue());
        wallLines[1].putTile(Tile.GREEN);
        wallLines[3].putTile(Tile.BLACK);
        assertEquals("Method should add 7 points", 7, wallLines[0].putTile(Tile.YELLOW).getValue());
        assertEquals("Method should add 2 points", 2, wallLines[4].putTile(Tile.BLUE).getValue());
        assertEquals("Method should add 7 points", 7, wallLines[2].putTile(Tile.RED).getValue());

        assertEquals("WallLine state() test", "-LR--", wallLines[2].state());
        assertEquals("WallLine state() test", "RGIBL", wallLines[0].state());
    }

}
