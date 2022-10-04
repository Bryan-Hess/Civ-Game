package hotciv.standard;

import hotciv.framework.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DecideWinnerImpl implements DecideWinner{

    //Declarataion of private variables
    String civVariation;
    Collection<City> cityMap;

    Player firstOwner;
    Player name2;

    public DecideWinnerImpl(String civVariationIN){
        civVariation = civVariationIN;
    }

    public Player getWinner(int currentAge, WorldLayout worldLayout) {
        if(civVariation.equals(GameConstants.BETACIV)){ //BetaCiv's winner decided when all cities are owned by one player

            //Gets a list of all cities in the map
            cityMap = worldLayout.getCityList();
            Iterator itr = cityMap.iterator();
            City city1 = (City) itr.next();
            Player name1 = city1.getOwner();

            while(itr.hasNext()){ //While loop to traverse city list
                City city2 =(City) itr.next();
                name2 = city2.getOwner();
                if(!name1.equals(name2)){ //If the first city's owner doesn't equal the i'th city's owner, return no winner
                    return null;
                }
            }
            return name1; //If all cities have the same owner, that player wins

        } else if(civVariation.equals(GameConstants.ALPHACIV)){ //AlphaCiv's winner is Red at year 3000BC
            if(currentAge >= -3000) {
                return Player.RED;
            }else{
                return null;
            }
        }else
            return null;
    }
}
