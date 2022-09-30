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
            return Player.BLUE;
        }else {
            if(currentAge >= -3000) {
                return Player.RED;
            }else{
                return null;
            }
        }
    }
}
