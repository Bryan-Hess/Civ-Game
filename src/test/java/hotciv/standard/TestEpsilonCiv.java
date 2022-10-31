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

    @Before
    public void setUp() {
        stubAttackDiceRoll = new StubAttackDiceRoll();
        stubDefenseDiceRoll = new StubDefenseDiceRoll();
        game = new GameImpl(GameConstants.EPSILONCIV, stubAttackDiceRoll, stubDefenseDiceRoll);
        Position blueLegionStart = new Position(3,2);
        Position redSettlerStart = new Position(4,3);
        Position redArcherStart = new Position(2,0);
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
    public void shouldGiveSum1ForBlueAtP5_5() {
        AttackStrategyImpl attackStrategy = (AttackStrategyImpl) game.getAttackStrategy();
        assertThat("Blue unit at (5,5) should get +1 support",
                attackStrategy.getFriendlySupport(new Position(5, 5), game.getWorldLayout()), is(+1));
    }
}


class StubTile implements Tile {
    private String type;
    public StubTile(String type, int r, int c) { this.type = type; }
    public String getTypeString() { return type; }
}

class StubUnit implements Unit {
    private String type; private Player owner;
    public StubUnit(String type, Player owner) {
        this.type = type; this.owner = owner;
    }
    public String getTypeString() { return type; }
    public Player getOwner() { return owner; }
    public int getMoveCount() { return 0; }
    public int getDefensiveStrength() { return 0; }
    public int getAttackingStrength() { return 0; }

    public void countMove(){

    }

    public void setDefensiveStrength(int def) {

    }

    public void setAttackingStrength(int att) {

    }

    public void resetMoveCount() {

    }

    public void setkeepMoveToZero(boolean flag) {}

    public boolean getkeepMoveToZero() { return false;
    }
}


/** A test stub for testing the battle calculation methods in
 * Utility. The terrains are
 * 0,0 - forest;
 * 1,0 - hill;
 * 0,1 - plain;
 * 1,1 - city.
 *
 * Red has units on 2,3; 3,2; 3,3; blue one on 4,4
 */

class GameStubForBattleTesting implements Game {
    public Tile getTileAt(Position p) {
        if ( p.getRow() == 0 && p.getColumn() == 0 ) {
            return new StubTile(GameConstants.FOREST, 0, 0);
        }
        if ( p.getRow() == 1 && p.getColumn() == 0 ) {
            return new StubTile(GameConstants.HILLS, 1, 0);
        }
        return new StubTile(GameConstants.PLAINS, 0, 1);
    }
    public Unit getUnitAt(Position p) {
        if ( p.getRow() == 2 && p.getColumn() == 3 ||
                p.getRow() == 3 && p.getColumn() == 2 ||
                p.getRow() == 3 && p.getColumn() == 3 ) {
            return new StubUnit(GameConstants.ARCHER, Player.RED);
        }
        if ( p.getRow() == 4 && p.getColumn() == 4 ) {
            return new StubUnit(GameConstants.ARCHER, Player.BLUE);
        }
        return null;
    }
    public City getCityAt(Position p) {
        if ( p.getRow() == 1 && p.getColumn() == 1 ) {
            return new City() {
                public Player getOwner() { return Player.RED; }
                public int getSize() { return 1; }
                public int getTreasury() {
                    return 0;
                }
                public String getProduction() {
                    return null;
                }
                public String getWorkforceFocus() {
                    return null;
                }

                public void setProduction(String prod){}

                /** set treasury in this city.
                 * @int amount: amount of production to be added or removed from the treasury
                 */
                public void setTreasury(int amount){}
            };
        }
        return null;
    }

    // the rest is unused test stub methods...
    public void changeProductionInCityAt(Position p, String unitType) {}
    public void changeWorkForceFocusInCityAt(Position p, String balance) {}
    public void endOfTurn() {}
    public Player getPlayerInTurn() {return null;}
    public Player getWinner() {return null;}
    public int getAge() { return 0; }
    public boolean moveUnit(Position from, Position to) {return false;}
    public void performUnitActionAt( Position p ) {}

        @Override
        public Object getAttackStrategy() {
            return null;
        }

        @Override
        public WorldLayout getWorldLayout() {
            return null;
        }

    }

