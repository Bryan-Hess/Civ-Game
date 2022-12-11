package hotciv.visual.standard;

import hotciv.framework.*;

import java.util.Collection;
import java.util.Iterator;

public class DecideWinnerImpl implements DecideWinner {

    //Declaration of private variables
    String civVariation;
    Collection<City> cityMap;

    Player firstOwner;
    Player name2;

    public DecideWinnerImpl(String civVariationIN) {
        civVariation = civVariationIN;
    }

    public Player getWinner(int currentAge, WorldLayout worldLayout) {
        if (civVariation.equals(GameConstants.BETACIV)) { //BetaCiv's winner decided when all cities are owned by one player

            //Gets a list of all cities in the map in the Collection data type
            cityMap = worldLayout.getCityList();
            //Implement an iterator for the Collection item. This will allow the game to iterate easily through the Collection
            Iterator itr = cityMap.iterator();
            City city1 = (City) itr.next();

            // First, grab the owner at the first city in the Collection.
            // This will be compared to every other owner in the Collection.
            // If a discrepancy is discovered, that means there are more than one Players owning a city
            // This means the game is not over. If there is no difference in owner after all cities are checked, that means there is only one city owner left
            // This player wins the game in BetaCiv and their name is returned

            Player name1 = city1.getOwner();

            while (itr.hasNext()) { //While loop to traverse city list
                City city2 = (City) itr.next();
                name2 = city2.getOwner();
                if (!name1.equals(name2)) { //If the first city's owner doesn't equal the i'th city's owner, return no winner
                    return null;
                }
            }
            return name1; //If all cities have the same owner, that player wins

        } else if(civVariation.equals(GameConstants.EPSILONCIV)){
            boolean r = worldLayout.getWins(Player.RED) > 2;
            boolean b = worldLayout.getWins(Player.BLUE) > 2;
            boolean g = worldLayout.getWins(Player.GREEN) > 2;
            boolean y = worldLayout.getWins(Player.YELLOW) > 2;
            if(r)
                return Player.RED;
            else if(b)
                return Player.BLUE;
            else if(g)
                return Player.GREEN;
            else if(y)
                return Player.YELLOW;
            else
                return null;

        }
        else if(civVariation.equals(GameConstants.ZETACIV)){
            if (worldLayout.getRound() <= 20) {
                if(worldLayout.getRound() == 20){
                    worldLayout.resetWins(); //Resets wins for new win method Epsilon
                }
                DecideWinnerImpl betaStrat = new DecideWinnerImpl(GameConstants.BETACIV);
                return betaStrat.getWinner(currentAge, worldLayout);
            }
            else {
                DecideWinnerImpl epsilonStrat = new DecideWinnerImpl((GameConstants.EPSILONCIV));
                return epsilonStrat.getWinner(currentAge, worldLayout);
            }
        }
        else if (civVariation.equals(GameConstants.ALPHACIV) || civVariation.equals(GameConstants.GAMMACIV) || civVariation.equals(GameConstants.DELTACIV)) {
            if (currentAge >= -3000) {
                return Player.RED;
            } else {
                return null;
            }
        } else
            return null;
    }
}
