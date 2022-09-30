package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitImpl implements Unit {
    private Player owner;
    private String type;

    private int moveCount;
    private int attackStrength;
    private int defenseStrength;
    public UnitImpl(Player name, String unitType){
        owner = name;
        type = unitType;
        moveCount = 1;
    }
    @Override
    public String getTypeString() {
        return type;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getMoveCount() {
        return moveCount;
    }

    @Override
    public int getDefensiveStrength() {
        return defenseStrength;
    }

    @Override
    public int getAttackingStrength() {
        return attackStrength;
    }

    public void resetMoveCount() {
        moveCount = 1;
    }

    public void countMove(){
        moveCount = moveCount - 1;
    }

}


