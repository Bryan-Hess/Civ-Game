package hotciv.standard;

import hotciv.framework.DiceRoll;

public class DiceRollImpl {

    public int getRoll(){
        int roll = (int) (Math.random()*6 - 1);

        return roll;
    }
}
