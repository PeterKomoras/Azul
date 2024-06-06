package sk.uniba.fmph.dcs;

import java.util.Optional;

public class FinalPointsCalculation {
    public static Points getPoints(Optional<Tile>[][] wall){
        HorizontalLineRule horizontalLineRule = new HorizontalLineRule();
        VerticalLineRule verticalLineRule = new VerticalLineRule();
        FullColorRule fullColorRule = new FullColorRule();

        CompositePointsCalculation compositePointsCalculation = new CompositePointsCalculation();
        compositePointsCalculation.addRule(horizontalLineRule);
        compositePointsCalculation.addRule(verticalLineRule);
        compositePointsCalculation.addRule(fullColorRule);

        int sum = compositePointsCalculation.calculatePoints(wall);

        return new Points(sum);
    }
}
