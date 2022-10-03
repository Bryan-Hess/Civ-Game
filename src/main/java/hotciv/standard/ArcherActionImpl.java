package hotciv.standard;

import hotciv.framework.*;

public class ArcherActionImpl implements ArcherAction {

    String civVariation;

    public ArcherActionImpl(String civVariationIN){
        civVariation = civVariationIN;
    }

    public void fortify(Position p, WorldLayout worldLayout) {
        if (civVariation.equals(GameConstants.GAMMACIV)){
            if(worldLayout.getUnitAt(p).getkeepMoveToZero()==false) { //Doubles defensive strength and immobilizes unit if unit is not fortified
                worldLayout.getUnitAt(p).setDefensiveStrength(worldLayout.getUnitAt(p).getDefensiveStrength() * 2);
                worldLayout.getUnitAt(p).setkeepMoveToZero(true);
            }else{ //Halves defensive strength and re-mobilizes the unit if fortified
                worldLayout.getUnitAt(p).setDefensiveStrength(worldLayout.getUnitAt(p).getDefensiveStrength() / 2);
                worldLayout.getUnitAt(p).setkeepMoveToZero(false);
            }
        }
        else{
            //do nothing
        }
    }
}
