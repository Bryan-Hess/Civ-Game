package hotciv.standard;

import hotciv.framework.DiceRoll;


// ================================== TEST STUBS ===
public class StubAttackDiceRoll implements DiceRoll {
    int roll;

    public void setRoll(int num) {
        roll = num;
    }

    @Override
    public int getRoll() {
        return roll;
    }
}
