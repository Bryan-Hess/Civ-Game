package hotciv.standard;

import hotciv.framework.DiceRoll;

public class DiceRollImpl implements DiceRoll{

    public int getRoll(){
        int roll = (int) (Math.random()*6 - 1);

        return roll;
    }
}
