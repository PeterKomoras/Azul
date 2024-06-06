package sk.uniba.fmph.dcs;

public interface TableAreaInterface {
    Tile[] take(int sourceId, int idx);
    boolean isRoundEnd();
    void startNewRound();
    String state();
}
