package sk.uniba.fmph.dcs;

public interface PatternLineInterface {
    void put(Tile[] tiles);
    Points finishRound();
    String state();
}
