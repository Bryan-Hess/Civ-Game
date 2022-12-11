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
    private boolean keepMoveToZero; //Used for fortify to keep a unit's move count 0
    private boolean passThroughTerrain; //Used for UFO's to be able to move over any terrain

    private int actionCount;

    public UnitImpl(Player name, String unitType){
        owner = name;
        type = unitType;
        moveCount = 1;
        keepMoveToZero = false;
        passThroughTerrain = false;
        actionCount = 1;

        //Switch case to set unit's initial attack/defense
        switch(unitType){
            case GameConstants.ARCHER:
                defenseStrength = 3;
                attackStrength = 2;
                break;

            case GameConstants.LEGION:
                defenseStrength = 2;
                attackStrength = 4;
                break;

            case GameConstants.SETTLER:
                defenseStrength = 3;
                attackStrength = 0;
                break;

            case GameConstants.UFO:
                defenseStrength = 8;
                attackStrength = 1;
                passThroughTerrain = true;
                moveCount = 2;
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
        if(!keepMoveToZero) //If statement to make sure fortified units keep a move count of 0
            if(type == GameConstants.UFO)
                moveCount = 2;
            else
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
    public void setKeepMoveToZero(boolean flag) {
        keepMoveToZero = flag;
    }
    public boolean getKeepMoveToZero() {
        return keepMoveToZero;
    }
    public boolean getPassThroughTerrain(){ return passThroughTerrain; }
    public void setPassThroughTerrain(boolean flag){ passThroughTerrain = flag; }

    public void countAction(){ actionCount=actionCount-1;}
    public void resetActionCount(){
        if(!keepMoveToZero)
            actionCount = 1;}

    public int getActionCount(){ return actionCount;}

}


