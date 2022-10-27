package hotciv.standard;

import hotciv.framework.AttackStrategy;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.WorldLayout;
import hotciv.standard.GameImpl;

import static java.util.Objects.isNull;

public class AttackStrategyImpl implements AttackStrategy {
    String civVariation;

    public AttackStrategyImpl (String civVariationIN) {
        civVariation = civVariationIN;
    }

    public boolean attackUnit(Position from, Position to, WorldLayout worldLayout) {
        if(civVariation.equals(GameConstants.EPSILONCIV)) {
            int defender_strength = (worldLayout.getUnitAt(to).getDefensiveStrength() + getFriendlySupport(to, worldLayout)) * getTerrainFactor(to, worldLayout);
            int attacker_strength = (worldLayout.getUnitAt(from).getAttackingStrength() + getFriendlySupport(from, worldLayout)) * getTerrainFactor(from, worldLayout);

            if (attacker_strength > defender_strength)
                return true;
            else
                return false;
        }else
            worldLayout.removeUnitAt(to);
            worldLayout.moveUnitTo(to,from);
            worldLayout.removeUnitAt(from);
            worldLayout.getUnitAt(to).countMove();
            return true;
    }

    private int getFriendlySupport(Position p, WorldLayout worldLayout) {
        //create iteration bounds "around" the city
        int[] rowChange = new int[]{-1, -1, 0, +1, +1, +1, 0, -1};
        int[] columnChange = new int[]{0, +1, +1, +1, 0, -1, -1, -1};

        //Itterate around position
        int supporting = 0;
        for (int i = 0; i < rowChange.length; i++) {
            int row = p.getRow() + rowChange[i];
            int col = p.getColumn() + columnChange[i];
            if (row>=0 && col>=0 && row<GameConstants.WORLDSIZE && col<GameConstants.WORLDSIZE){
                //Numer of supporting units
                Position nextPos = new Position(row, col);
                if(!isNull(worldLayout.getUnitAt(nextPos))){
                    if (worldLayout.getUnitAt(p).getOwner().equals(worldLayout.getUnitAt(nextPos).getOwner())) {
                        supporting += 1;
                    }
                }
            }
        }

        return supporting;
    }

    private int getTerrainFactor(Position p, WorldLayout worldLayout) {

        switch (worldLayout.getTileAt(p).getTypeString()){
            case GameConstants.HILLS:
                return 2;
            case GameConstants.FOREST:
                return 2;
        }

        if(worldLayout.getCityAt(p) != null)
            return 3;

        return 1;
    }

}
