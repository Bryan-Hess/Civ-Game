package hotciv.standard;

import hotciv.framework.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

        } else { //AlphaCiv's winner is Red at year 3000BC
            if (currentAge >= -3000) {
                return Player.RED;
            } else {
                return null;
            }
        }
    }

}
