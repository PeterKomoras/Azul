package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class WallLine implements WallLineInterface{
    private ArrayList tiles;
    private int countColours;
    private WallLine lineUp;
    private WallLine lineDown;
    private Tile[] currentWallLine;

    public WallLine(Tile[] tiles, WallLine lineup, WallLine linedown) {
        this.lineDown = linedown;
        this.lineUp = lineup;
        this.tiles = new ArrayList<>(Arrays.asList(tiles));

        this.countColours = Tile.values().length - 1;
        this.currentWallLine = new Tile[countColours];
    }
    public void setUp(WallLine line){
        this.lineUp = line;
    }
    public void setDown(WallLine line){
        this.lineDown = line;
    }
    public WallLine getUp(){
        return lineUp;
    }
    public WallLine getDown(){
        return lineDown;
    }

    public boolean canPutTile(Tile tile) {
        int index = tiles.indexOf(tile);
        if (index != -1) {
            if (currentWallLine[index] != null) {
                return false;
            }
            return true;
        }
        return false;
    }

    public Optional<Tile>[] getTiles() {
        Optional<Tile>[] line = new Optional[countColours];
        for (int i = 0; i < countColours; i++) {
            line[i] = Optional.ofNullable(currentWallLine[i]);
        }
        return line;
    }

    public Points putTile(Tile tile){
        int p = 0;
        int k = 0;
        int idx = tiles.indexOf(tile);
        if(canPutTile(tile)) {
            currentWallLine[idx] = tile;
            p++;
            k = 1;
            if(idx != 0){
                int i = idx - 1;
                while(i >= 0 & currentWallLine[i] != null) {
                    p++;
                    i--;
                    k = 0;
                    if(i < 0){
                        break;
                    }
                }
            }
            if(idx != countColours - 1){
                int i = idx + 1;
                while(i <= countColours - 1 & currentWallLine[i] != null){
                    p++;
                    i++;
                    k = 0;
                    if(i == countColours){
                        break;
                    }
                }

            }
            if(lineDown != null){
                Optional<Tile>[] down = lineDown.getTiles();
                if(down[idx].isPresent()){
                    p++;
                    if(k == 0){
                        p++;
                        k = 1;
                    }
                    p = columnDown(lineDown, idx, p);
                }
            }
            if(lineUp != null){
                Optional<Tile>[] up = lineUp.getTiles();
                if(up[idx].isPresent()){
                    p++;
                    if(k == 0){
                        p++;
                    }
                    p = columnUp(lineUp, idx, p);
                }
            }
            return new Points(p);
        }
        return new Points(0);
    }
    private int columnUp(WallLine wallLine, int idx, int p){
        if(wallLine.lineUp != null){
            Optional<Tile>[] up = wallLine.getUp().getTiles();
            if(up[idx].isPresent()){
                p++;
                p = columnUp(wallLine.getUp(), idx, p);
                return p;
           }
            return p;
        }
        return p;
    }
    private int columnDown(WallLine wallLine, int idx, int p){
        if(wallLine.lineDown != null){
            Optional<Tile>[] down = wallLine.getDown().getTiles();
            if(down[idx].isPresent()){
                p++;
                p = columnDown(wallLine.getDown(), idx, p);
                return p;
            }
            return p;
        }
        return p;
    }

    public String state(){
        StringBuilder toReturn = new StringBuilder();
        for (Tile tile : currentWallLine){
            if (tile == null)
                toReturn.append("-");
            else
                toReturn.append(tile);
        }
        return toReturn.toString();
    }
}