package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameObserver;
import org.junit.Before;

public class TestSemiCivGUI {
    private Game game;
    private StubAttackDiceRoll stubAttackDiceRoll;
    private StubDefenseDiceRoll stubDefenseDiceRoll;

    private GameObserver gameObs;

    @Before
    public void setUp() {
        stubAttackDiceRoll = new StubAttackDiceRoll();
        stubDefenseDiceRoll = new StubDefenseDiceRoll();

        game = new GameImpl(new SemiCivFactory(stubAttackDiceRoll,stubDefenseDiceRoll));
    }

}
