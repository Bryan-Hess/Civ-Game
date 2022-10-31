package hotciv.standard;

import hotciv.framework.DiceRoll;

/**
 * @Test public void shouldGiveSum0ForBlueAtP2_4() {
 * AttackStrategyImpl attackStrategy = (AttackStrategyImpl) game.getAttackStrategy();
 * assertThat("Blue unit at (2,4) should get +0 support",
 * Utility2.getFriendlySupport( game, new Position(2,4), Player.BLUE), is(+0));
 * }
 * @Test public void shouldGiveSum2ForRedAtP2_4() {
 * assertThat("Red unit at (2,4) should get +2 support",
 * Utility2.getFriendlySupport( game, new Position(2,4), Player.RED), is(+2));
 * }
 * @Test public void shouldGiveSum3ForRedAtP2_2() {
 * assertThat("Red unit at (2,2) should get +3 support",
 * Utility2.getFriendlySupport( game, new Position(2,2), Player.RED), is(+3));
 * }
 * }
 */
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
