package sk.uniba.fmph.dcs;

interface BoardInterface {
    void put(int destinationIdx, Tile[] tiles);
    FinishRoundResult finishRound();
    void endGame();
    String state();
    Points getPoints();
}