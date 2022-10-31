package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestBetaCiv {

    private Game game;
    private StubAttackDiceRoll stubAttackDiceRoll;
    private StubDefenseDiceRoll stubDefenseDiceRoll;

    @Before
    public void setUp() {
        stubAttackDiceRoll = new StubAttackDiceRoll();
        stubDefenseDiceRoll = new StubDefenseDiceRoll();
        game = new GameImpl(GameConstants.BETACIV,stubAttackDiceRoll,stubDefenseDiceRoll);
    }

    @Test
    public void verifyBetaCivAgingVariation(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getAge(), is(-4000));
        game.endOfTurn();
        assertThat(game.getAge(), is(-3900));
        for(int i = 0; i < 38; i++){
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(-100));
        game.endOfTurn();
        assertThat(game.getAge(), is(-1));
        game.endOfTurn();
        assertThat(game.getAge(), is(1));
        game.endOfTurn();
        assertThat(game.getAge(), is(50));
        game.endOfTurn();
        assertThat(game.getAge(), is(100));
        for(int j = 0; j < 33; j++){
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(1750));
        game.endOfTurn();
        assertThat(game.getAge(), is(1775));
        for(int k = 0; k < 5; k++){
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(1900));
        game.endOfTurn();
        assertThat(game.getAge(), is(1905));
        for(int l = 0; l < 13; l++){
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(1970));
        game.endOfTurn();
        assertThat(game.getAge(), is(1971));
        game.endOfTurn();
        assertThat(game.getAge(), is(1972));
    }
    @Test
    public void verifyWinnerBlueifRedTakesAllCities(){
        assertThat(game, is(notNullValue()));
        //assertThat(game.getWinner(), is(nullValue()));
        assertThat(game.getUnitAt(new Position(2,0)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.moveUnit(new Position(2,0),(new Position(2,1))), is(true));
        assertThat(game.getUnitAt(new Position(2,1)).getTypeString(), is(GameConstants.ARCHER));
        game.endOfTurn();
        game.endOfTurn();
       // assertThat(game.getWinner(), is(nullValue()));
        assertThat(game.getUnitAt(new Position(2,1)).getMoveCount(), is(1));
        assertThat(game.moveUnit(new Position(2,1),(new Position(3,1))), is(true));
        game.endOfTurn();
        game.endOfTurn();
        //assertThat(game.getWinner(), is(nullValue()));
        assertThat(game.getUnitAt(new Position(3,1)).getMoveCount(), is(1));
        assertThat(game.moveUnit(new Position(3,1),(new Position(4,1))), is(true));
        assertThat(game.getUnitAt(new Position(4,1)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.RED));
        //assertThat(game.getWinner(), is(Player.RED));
    }

    // Same test as AlphaCiv. Confirm same starting layout for BetaCiv
    @Test
    public void verifyStartingTownLayout(){
        assertThat(game, is(notNullValue()));
        for(int c = 0; c < GameConstants.WORLDSIZE; c++){
            for(int r = 0; r < GameConstants.WORLDSIZE; r++) {
                if(r == 1 & c == 1){
                    assertThat(game.getCityAt(new Position(1,1)).getOwner(), is(Player.RED));
                }else if(r == 4 & c == 1){
                    assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.BLUE));
                }else{
                    assertThat(game.getCityAt(new Position(r,c)), is(nullValue()));
                }
            }
        }
    }

    //This tests a proper Settler action according to the Beta Civ requirements
    //Nothing happens
    @Test
    public void testSettlerAction(){

        assertThat(game, is(notNullValue()));
        assertThat(game.getUnitAt(new Position(4,3)).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getCityAt(new Position(4,3)), is(nullValue()));
        game.performUnitActionAt(new Position(4,3));
        assertThat(game.getUnitAt(new Position(4,3)).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getCityAt(new Position(4,3)), is(nullValue()));
    }

    //This tests a proper Archer action according to the Beta Civ requirements
    //nothing happens
    @Test
    public void testArcherAction(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getUnitAt(new Position(2,0)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(2,0)).getDefensiveStrength(), is(3));
        game.performUnitActionAt(new Position(2,0));
        assertThat(game.getUnitAt(new Position(2,0)).getDefensiveStrength(), is(3));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.moveUnit(new Position(2,0),(new Position(3,0))), is(true));
        assertThat(game.getUnitAt(new Position(3,0)).getTypeString(), is(GameConstants.ARCHER));
        game.performUnitActionAt(new Position(3,0));
        assertThat(game.getUnitAt(new Position(3,0)).getDefensiveStrength(), is(3));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.moveUnit(new Position(3,0),(new Position(2,0))), is(true));
    }
}
