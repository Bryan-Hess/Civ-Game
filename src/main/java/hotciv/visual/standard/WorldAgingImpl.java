package hotciv.visual.standard;

import hotciv.framework.WorldAging;
import hotciv.framework.GameConstants;


public class WorldAgingImpl implements WorldAging {

    String civVariation;

    public WorldAgingImpl(String civVariationIN){
        civVariation = civVariationIN;
    }
    public int incrementAge(int currentAge) {
        if(civVariation.equals(GameConstants.BETACIV)){ //BetaCiv's progressive aging algorithm
            if( currentAge >= -4000 && currentAge < -100 ){
                return currentAge + 100;
            }else if( currentAge == -100 ){
                return -1;
            }else if( currentAge == -1 ){
                return 1;
            }else if( currentAge == 1 ){
                return 50;
            }else if( currentAge >= 50 && currentAge < 1750 ){
                return currentAge + 50;
            }else if( currentAge >= 1750 && currentAge < 1900 ){
                return currentAge + 25;
            }else if( currentAge >= 1900 && currentAge < 1970 ){
                return currentAge + 5;
            }else{
                return currentAge + 1;
            }
        }else if(civVariation.equals(GameConstants.ALPHACIV)){

            return currentAge + 100;
        }else
        return currentAge + 100;
    }
}
