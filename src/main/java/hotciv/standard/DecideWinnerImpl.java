package hotciv.standard;

import hotciv.framework.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DecideWinnerImpl implements DecideWinner{

    String civVariation;
    Collection<City> cityMap;

    Player firstOwner;
    Player name2;

    public DecideWinnerImpl(String civVariationIN){
        civVariation = civVariationIN;
    }

    public Player getWinner(int currentAge, WorldLayout worldLayout) {
        if(civVariation.equals(GameConstants.BETACIV)){

            cityMap = worldLayout.getCityList();
            
            Iterator itr = cityMap.iterator();
            City city1 = (City) itr.next();
            Player name1 = city1.getOwner();
            while(itr.hasNext()){
                City city2 =(City) itr.next();
                name2 = city2.getOwner();
                if(!name1.equals(name2)){
                    return null;
                }
            }
            return name1;

        } else if(civVariation.equals(GameConstants.ALPHACIV)){
            if(currentAge >= -3000) {
                return Player.RED;
            }else{
                return null;
            }
        }else
            return null;
    }
}
