package hotciv.standard;

import hotciv.framework.*;

import static java.util.Objects.isNull;

public class AttackStrategyImpl implements AttackStrategy {
    String civVariation;
    private DiceRoll attackRoll;
    private DiceRoll defenseRoll;

    public AttackStrategyImpl (String civVariationIN, DiceRoll attackRoll2, DiceRoll defenseRoll2) {
        civVariation = civVariationIN;
        attackRoll = attackRoll2;
        defenseRoll = defenseRoll2;
    }

    public boolean attackUnit(Position from, Position to, WorldLayout worldLayout) {
        if(civVariation.equals(GameConstants.EPSILONCIV)) {
            int defender_strength = getDefenseStrength(to,worldLayout);
            int attacker_strength = getAttackStrength(from,worldLayout);
            int attackPower = attackRoll.getRoll();
            int defensePower = defenseRoll.getRoll();

            if(attacker_strength*attackPower > defender_strength*defensePower) {
                processSuccessfulAttack(from, to, worldLayout);
                return true;
            }
            else { //Kills attacking unit
                worldLayout.removeUnitAt(from);
                return false;
            }
        }else {
            processSuccessfulAttack(from, to, worldLayout);
            return true;
        }
    }

    private void processSuccessfulAttack(Position from, Position to, WorldLayout worldLayout){
        worldLayout.removeUnitAt(to);
        worldLayout.moveUnitTo(to,from);
        worldLayout.removeUnitAt(from);
        worldLayout.getUnitAt(to).countMove();
        worldLayout.addWin(worldLayout.getUnitAt(to).getOwner());

    }

    public int getFriendlySupport(Position p, WorldLayout worldLayout) {
        //create iteration bounds "around" the city
        int[] rowChange = new int[]{-1, -1, 0, +1, +1, +1, 0, -1};
        int[] columnChange = new int[]{0, +1, +1, +1, 0, -1, -1, -1};
        //Iterate around position
        int supporting = 0;
        for (int i = 0; i < rowChange.length; i++) {
            int row = p.getRow() + rowChange[i];
            int col = p.getColumn() + columnChange[i];
            if (row>=0 && col>=0 && row<GameConstants.WORLDSIZE && col<GameConstants.WORLDSIZE){
                //Number of supporting units
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

    public int getAttackStrength(Position p, WorldLayout worldLayout){
        int sum = (worldLayout.getUnitAt(p).getAttackingStrength() + getFriendlySupport(p, worldLayout)) * getTerrainFactor(p, worldLayout);
        return sum;
    }

    public int getDefenseStrength(Position p, WorldLayout worldLayout){
        int sum = (worldLayout.getUnitAt(p).getDefensiveStrength() + getFriendlySupport(p, worldLayout)) * getTerrainFactor(p, worldLayout);
        return sum;
    }
    public int getTerrainFactor(Position p, WorldLayout worldLayout) {

        if(worldLayout.getCityAt(p) != null)
            return 3;

        switch (worldLayout.getTileAt(p).getTypeString()){
            case GameConstants.HILLS:
            case GameConstants.FOREST:
                return 2;
        }
        return 1;
    }

}


