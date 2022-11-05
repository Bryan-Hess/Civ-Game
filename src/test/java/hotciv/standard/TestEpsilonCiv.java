package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TestEpsilonCiv {

    private Game game;
    private StubAttackDiceRoll stubAttackDiceRoll;
    private StubDefenseDiceRoll stubDefenseDiceRoll;
    private Position redSettlerStart,blueLegionStart,redArcherStart;

    @Before
    public void setUp() {
        stubAttackDiceRoll = new StubAttackDiceRoll();
        stubDefenseDiceRoll = new StubDefenseDiceRoll();
        
        game = new GameImpl(new EpsilonCivFactory(stubAttackDiceRoll,stubDefenseDiceRoll));

    }

    @Test
    public void shouldGiveCorrectTerrainFactors() {

        AttackStrategyImpl attackStrategy = (AttackStrategyImpl) game.getAttackStrategy();
        // plains have multiplier 1 - plains at tile 3,3
        assertThat(attackStrategy.getTerrainFactor(new Position(3, 3), game.getWorldLayout()), is(1));
        // hills have multiplier 2
        assertThat(attackStrategy.getTerrainFactor(new Position(0, 1), game.getWorldLayout()), is(2));

        // forest have multiplier 2 - no forest in epsilon civ map - will test with test stubs


        // cities have multiplier 3
        assertThat(attackStrategy.getTerrainFactor(new Position(1, 1), game.getWorldLayout()), is(3));

    }

    @Test
    public void shouldGiveSum0ForBlueAtP8_8() {
        AttackStrategyImpl attackStrategy = (AttackStrategyImpl) game.getAttackStrategy();
        assertThat("Blue unit at (8,8) should get +0 support",
                attackStrategy.getFriendlySupport(new Position(8, 8), game.getWorldLayout()), is(0));
    }

    @Test
    public void shouldGiveSum2ForRedAtP9_9() {
        AttackStrategyImpl attackStrategy = (AttackStrategyImpl) game.getAttackStrategy();
        assertThat("Red unit at (9,9) should get +2 support",
                attackStrategy.getFriendlySupport(new Position(9, 9), game.getWorldLayout()), is(+2));
    }

    @Test
    public void shouldGive1forPlains(){
        AttackStrategyImpl attackStrategy = (AttackStrategyImpl) game.getAttackStrategy();
        assertThat("unit at tile 9,9 is on plains, should get 1 for terrain factor",
                attackStrategy.getTerrainFactor(new Position(9, 9), game.getWorldLayout()), is(+1));
    }
    @Test

    public void shouldGiveAttackStrengthOf6atP9_9(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        AttackStrategyImpl attackStrategy = (AttackStrategyImpl) game.getAttackStrategy();
        assertThat(attackStrategy.getAttackStrength(new Position(9,9), game.getWorldLayout()),is(+6));
    }
    @Test
    public void shouldGiveDefensiveStrengthOf2atP8_8(){
        assertThat(game, is(notNullValue()));
        AttackStrategyImpl attackStrategy = (AttackStrategyImpl) game.getAttackStrategy();
        assertThat(attackStrategy.getDefenseStrength(new Position(8,8), game.getWorldLayout()),is(+2));
    }

    @Test
    public void testDiceRollOf5(){
        stubAttackDiceRoll.setRoll(5);
        assertThat(stubAttackDiceRoll.getRoll(),is(5));
    }

    @Test
    public void testDiceRollof1(){
        stubAttackDiceRoll.setRoll(1);
        assertThat(stubAttackDiceRoll.getRoll(),is(1));
    }

    @Test
    public void rollOf1ShouldLose(){
        stubAttackDiceRoll.setRoll(1);
        stubDefenseDiceRoll.setRoll(5);

        assertThat(game.moveUnit(new Position(9,9),new Position(8,8)),is(false));

    }
    @Test
    public void rollOf6ShouldWin(){
        stubAttackDiceRoll.setRoll(6);
        stubDefenseDiceRoll.setRoll(1);

        assertThat(game.moveUnit(new Position(9,9),new Position(8,8)),is(true));
        assertThat(game.getUnitAt(new Position(8,8)).getOwner(),is(Player.RED));
    }

    @Test
    public void win3toWin(){
        stubAttackDiceRoll.setRoll(6);
        stubDefenseDiceRoll.setRoll(1);

        assertThat(game.moveUnit(new Position(9,9),new Position(8,8)),is(true));
        assertThat(game.getUnitAt(new Position(8,8)).getOwner(),is(Player.RED));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.moveUnit(new Position(2,0),new Position(3,1)),is(true));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.moveUnit(new Position(3,1),new Position(3,2)),is(true));
        assertThat(game.getUnitAt(new Position(3,2)).getOwner(),is(Player.RED));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.moveUnit(new Position(3,2),new Position(3,3)),is(true));

        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.moveUnit(new Position(3,3),new Position(4,4)),is(true));

        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void ifTheDefenderWinsTheAttackerShouldBeRemoved() {
        assertThat(game, is(notNullValue()));
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        assertThat(game.moveUnit(new Position(4,3),new Position(3,2)),is(false));
        //game.moveUnit(redSettlerStart, blueLegionStart);
        assertThat(game.getUnitAt(new Position(3,2)).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(new Position(4,3)),is(nullValue()));
    }



}

