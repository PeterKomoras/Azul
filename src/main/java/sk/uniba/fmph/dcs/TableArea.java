package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class TableArea implements TableAreaInterface {
    private final ArrayList<TyleSource> tyleSources;

    public TableArea(ArrayList<TyleSource> tyleSources) {
        this.tyleSources = tyleSources;
    }
    @Override
    public Tile[] take(int sourceIdx, int idx) {
        if (sourceIdx >= 0 && sourceIdx < tyleSources.size()) {
            TyleSource selectedSource = tyleSources.get(sourceIdx);
            return selectedSource.take(idx);
        } else {
            return new Tile[0];
        }
    }
    @Override
    public boolean isRoundEnd() {
        for (TyleSource tyleSource : tyleSources) {
            if (!tyleSource.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void startNewRound() {
        for (TyleSource tyleSource : tyleSources) {
            tyleSource.startNewRound();
        }
    }
    @Override
    public String state() {
        StringBuilder stateBuilder = new StringBuilder();
        stateBuilder.append("Table:\n");
        for (TyleSource tyleSource : tyleSources) {
            stateBuilder.append(tyleSource.state());
            stateBuilder.append("\n");
        }
        return stateBuilder.toString();
    }
}
