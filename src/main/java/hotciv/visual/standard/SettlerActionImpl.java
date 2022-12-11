package hotciv.visual.standard;

import hotciv.framework.*;

public class SettlerActionImpl implements SettlerAction {
    String civVariation;

    public SettlerActionImpl(String civVariationIN){
        civVariation = civVariationIN;
    }
    public void buildCity(Position p, WorldLayout worldLayout) {
        if (civVariation.equals(GameConstants.GAMMACIV)){ //GammaCiv allows settlers to build cities
            Player tempOwner = worldLayout.getUnitAt(p).getOwner();
            //Removes the settler and places a city in their stead
            worldLayout.removeUnitAt(p);
            worldLayout.addCityAt(p, tempOwner);
        }
        else{
            //do nothing
        }
    }

}
