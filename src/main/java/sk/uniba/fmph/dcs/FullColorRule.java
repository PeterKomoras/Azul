package sk.uniba.fmph.dcs;

import java.util.Optional;

public class FullColorRule implements ScoringRule{
    @Override
    public int calculatePoints(Optional<Tile>[][] wall){
        int sum = 0;

        for (int i = 0; i < wall.length; i++) {
            int inOneColor = 0;

            for (int j = 0; j < wall.length; j++) {
                int rowIndex = j;
                int colIndex = (i + j) % wall.length;

                if (wall[rowIndex][colIndex].isPresent()) {
                    inOneColor++;
                } else {
                    inOneColor = 0;
                }
            }
            if (inOneColor == wall.length) {
                sum += EndGameScoringConstants.FULL_COLOR_SET_POINTS;
            }
        }
        return sum;
    }
}
