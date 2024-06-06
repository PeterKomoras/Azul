package sk.uniba.fmph.dcs;

import java.util.Optional;

public class VerticalLineRule implements ScoringRule{
    @Override
    public int calculatePoints(Optional<Tile>[][] wall){
        int sum = 0;
        boolean complete = true;
        for(int i = 0; i < wall.length; i++){
            for(int j = 0; j < wall.length; j++){
                if(!wall[j][i].isPresent()){
                    complete = false;
                    break;
                }
            }
            if(complete){
                sum += EndGameScoringConstants.VERTICAL_LINE_POINTS;
            }
            complete = true;
        }
        return sum;
    }
}
