package hotciv.standard;

import hotciv.framework.*;

public class UFOActionImpl implements UFOAction {
    String civVariation;

    public UFOActionImpl(String civVariationIN){
        civVariation = civVariationIN;
    }

    public void abduct(Position p, WorldLayout worldLayout, Player currentPlayer) {
        if(civVariation.equals(GameConstants.THETACIV)){
            if(worldLayout.getCityAt(p)!=null&&!currentPlayer.equals(worldLayout.getCityAt(p).getOwner())){ //If hovering over enemy city
                if(worldLayout.getCityAt(p).getSize()>1) //If citypop>1, remove 1 citizen
                    worldLayout.getCityAt(p).setSize(worldLayout.getCityAt(p).getSize()-1);
                else{ //If citypop is reduced to zero, remove city
                    worldLayout.removeCityAt(p);
                }
                worldLayout.getUnitAt(p).countMove(); //Need to count move twice to allow UFO to move twice
                worldLayout.getUnitAt(p).countMove();
            } else if (worldLayout.getTileAt(p).getTypeString().equals(GameConstants.FOREST)) { //If hovering over forest, turn to plains
                worldLayout.setTile(p, GameConstants.PLAINS);
                worldLayout.getUnitAt(p).countMove();
                worldLayout.getUnitAt(p).countMove();
            }
        }else{
            //do nothing
        }
    }

}
