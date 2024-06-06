package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class Game implements GameInterface{
    private final TableAreaInterface tableArea;
    private final ArrayList<BoardInterface> boards;
    private final GameObserverInterface gameObserver;
    private int currentPlayer = 0;
    int startingPlayer = 0;
    private boolean isEndGame = false;

    public Game(ArrayList<BoardInterface> boards, TableAreaInterface tableArea, GameObserverInterface gameObserver) {
        this.tableArea = tableArea;
        this.boards = boards;
        this.gameObserver = gameObserver;
    }

    /**
     * @param playerId 0,1,2,3
     * @param sourceId 0,1,2, ... factories + tableCenter - 1
     * @param idx      1,2...5
     * @param destinationIxd 0,1,...4
     */
    @Override
    public boolean take(int playerId, int sourceId, int idx, int destinationIxd) {
        if (currentPlayer != playerId || isEndGame) return false;
        BoardInterface currentBoard = boards.get(playerId);
        // incorrect ixd
        if (idx < 1 || 5 < idx) return false;
        Tile[] takenTiles = tableArea.take(sourceId, idx);
        // the chosen area without the ixd tile
        if (takenTiles.length == 0) return false;
        if (takenTiles[takenTiles.length-1].equals(Tile.STARTING_PLAYER)) startingPlayer = currentPlayer;

        // send takenTiles to board
        currentBoard.put(destinationIxd, takenTiles);
        currentPlayer = (currentPlayer + 1) % boards.size();

        if (tableArea.isRoundEnd()) {
            for (BoardInterface b : boards) {
                if (b.finishRound() == FinishRoundResult.GAME_FINISHED) { isEndGame = true; }
            }
            if (isEndGame) {
                for (BoardInterface b : boards) b.endGame();
            } else {
                tableArea.startNewRound();
                currentPlayer = startingPlayer;
            }
        }


        return true;
    }

    public boolean isGameOver() {
        return isEndGame;
    }

    public int getCurrentPlayerId() {
        return currentPlayer;
    }

    public GameObserverInterface getGameObserver() {
        return gameObserver;
    }
}
