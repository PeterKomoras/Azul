package sk.uniba.fmph.dcs;

abstract class TyleSource {
    abstract public Tile[] take(int idx);

    abstract public boolean isEmpty();

    abstract public void startNewRound();

    abstract public String state();
}