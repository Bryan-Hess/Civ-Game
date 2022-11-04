package hotciv.standard;

import hotciv.framework.DiceRoll;

public class StubDefenseDiceRoll implements DiceRoll {
    int roll;

    public void setRoll(int num) {
        roll = num;
    }

    @Override
    public int getRoll() {
        return roll;
    }
}
