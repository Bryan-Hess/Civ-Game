package hotciv.standard;

import hotciv.framework.DecideWinner;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import java.util.List;

public class DecideWinnerImpl implements DecideWinner{

    String civVariation;

    public DecideWinnerImpl(String civVariationIN){
        civVariation = civVariationIN;
    }

    public Player getWinner(int currentAge, List<CityImpl> cities) {
        if(civVariation.equals(GameConstants.BETACIV)){
            for(CityImpl s:cities){
                if (!s.getOwner().equals(cities.get(0).getOwner()))
                    return null;
                else
                    return cities.get(0).getOwner();
            }
            return null;

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
