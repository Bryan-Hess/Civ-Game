package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitImpl implements Unit {
    //Declaration of private variables
    private Player owner;
    private String type;
    private int moveCount;
    private int attackStrength;
    private int defenseStrength;
    private boolean keepMoveToZero; //Used for fority to keep a unit's move count 0

    public UnitImpl(Player name, String unitType){
        owner = name;
        type = unitType;
        moveCount = 1;
        keepMoveToZero = false;

        //Switch case to set unit's initial attack/defense
        switch(unitType){
            case "archer":
                defenseStrength = 3;
                attackStrength = 2;
                break;

            case "legion":
                defenseStrength = 2;
                attackStrength = 4;
                break;

            case "settler":
                defenseStrength = 3;
                attackStrength = 0;
                break;
        }
    }

    //Setters and getters for unit variables
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
        if(keepMoveToZero==false) //If statement to make sure fortified units keep a move count of 0
            moveCount = 1;
    }

    public void countMove(){
        moveCount = moveCount - 1;
    }

    public void setDefensiveStrength(int def) {
        defenseStrength = def;
    }

    public void setAttackingStrength(int att) {
        attackStrength = att;
    }

    public void setkeepMoveToZero(boolean flag) {
        keepMoveToZero = flag;
    }

    public boolean getkeepMoveToZero() {
        return keepMoveToZero;
    }

}


