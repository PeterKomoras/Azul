package sk.uniba.fmph.dcs;

import java.util.Optional;

public class HorizontalLineRule implements ScoringRule{
    @Override
    public int calculatePoints(Optional<Tile>[][] wall){
        int sum = 0;
        boolean complete = true;
        for(int i = 0; i < wall.length; i++){
            for(int j = 0; j < wall[i].length; j++){
                if(!wall[i][j].isPresent()){
                    complete = false;
                    break;
                }
            }
            if(complete){
                sum += EndGameScoringConstants.HORIZONTAL_LINE_POINTS;
            }
            complete = true;
        }
        return sum;
    }
}
