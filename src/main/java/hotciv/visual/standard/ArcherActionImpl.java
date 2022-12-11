package hotciv.visual.standard;

import hotciv.framework.*;

public class ArcherActionImpl implements ArcherAction {
    String civVariation;
    public ArcherActionImpl(String civVariationIN){
        civVariation = civVariationIN;
    }

    public void fortify(Position p, WorldLayout worldLayout) {
        if (civVariation.equals(GameConstants.GAMMACIV)){ //GammaCiv allows for archer fortification
            if(!worldLayout.getUnitAt(p).getKeepMoveToZero()) { //Doubles defensive strength and immobilizes unit if unit is not fortified
                worldLayout.getUnitAt(p).setDefensiveStrength(worldLayout.getUnitAt(p).getDefensiveStrength() * 2);
                worldLayout.getUnitAt(p).setKeepMoveToZero(true);
                worldLayout.getUnitAt(p).countMove();
            }else{ //Halves defensive strength and re-mobilizes the unit if fortified
                worldLayout.getUnitAt(p).setDefensiveStrength(worldLayout.getUnitAt(p).getDefensiveStrength() / 2);
                worldLayout.getUnitAt(p).setKeepMoveToZero(false);
            }
        }
        else{
            //do nothing
        }
    }
}
