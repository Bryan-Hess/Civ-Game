package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.UFOAction;
import hotciv.framework.WorldLayout;

public class UFOActionImpl implements UFOAction {
    String civVariation;

    public UFOActionImpl(String civVariationIN){
        civVariation = civVariationIN;
    }

    public void abduct(Position p, WorldLayout worldLayout) {
        if(civVariation.equals(GameConstants.THETACIV)){
            // if ufo over city with no unit remove 1 pop if city 0 pop remove city
        }else{
            //do nothing
        }
    }
}
