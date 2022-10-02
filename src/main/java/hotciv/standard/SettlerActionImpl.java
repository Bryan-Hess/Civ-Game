package hotciv.standard;

import hotciv.framework.*;

public class SettlerActionImpl implements SettlerAction {

    String civVariation;

    public SettlerActionImpl(String civVariationIN){
        civVariation = civVariationIN;
    }

    public void buildCity(Position p, WorldLayout worldLayout) {

        if (civVariation.equals(GameConstants.GAMMACIV)){
            Player tempOwner = worldLayout.getUnitAt(p).getOwner();
            worldLayout.removeUnitAt(p);
            worldLayout.addCityAt(p, tempOwner);
        }
        else{
            //do nothing
        }

    }
}
