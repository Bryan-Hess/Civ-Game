package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.DeltaCivFactory;
import hotciv.standard.GameImpl;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestDeltaCiv {

    private Game game;

    private StubAttackDiceRoll stubAttackDiceRoll;
    private StubDefenseDiceRoll stubDefenseDiceRoll;
    @Before
    public void setUp() {
        stubAttackDiceRoll = new StubAttackDiceRoll();
        stubDefenseDiceRoll = new StubDefenseDiceRoll();
        game = new GameImpl(new DeltaCivFactory());
    }

    @Test
    public void checkStartingLayout(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getTileAt(new Position(0,1)).getTypeString(), is(GameConstants.OCEANS));

        assertThat(game.getTileAt(new Position(0,3)).getTypeString(), is(GameConstants.PLAINS));
        assertThat(game.getTileAt(new Position(2,2)).getTypeString(), is(GameConstants.PLAINS));
        assertThat(game.getTileAt(new Position(4,4)).getTypeString(), is(GameConstants.FOREST));
        assertThat(game.getTileAt(new Position(3,5)).getTypeString(), is(GameConstants.MOUNTAINS));
        assertThat(game.getTileAt(new Position(15,15)).getTypeString(), is(GameConstants.OCEANS));

    }

    @Test
    public void verifyStartingUnitLayout(){
        assertThat(game, is(notNullValue()));
        for(int c = 0; c < GameConstants.WORLDSIZE; c++){
            for(int r = 0; r < GameConstants.WORLDSIZE; r++){
                if(r == 3 & c == 8){
                    assertThat(game.getUnitAt(new Position(3,8)).getTypeString(), is(GameConstants.ARCHER));
                    assertThat(game.getUnitAt(new Position(3,8)).getOwner(), is(Player.RED));
                }else if(r == 4 & c == 4){
                    assertThat(game.getUnitAt(new Position(4,4)).getTypeString(), is(GameConstants.LEGION));
                    assertThat(game.getUnitAt(new Position(4,4)).getOwner(), is(Player.BLUE));
                }else if(r == 5 & c == 5){
                    assertThat(game.getUnitAt(new Position(5,5)).getTypeString(), is(GameConstants.SETTLER));
                    assertThat(game.getUnitAt(new Position(5,5)).getOwner(), is(Player.RED));
                }else{
                    assertThat(game.getUnitAt(new Position(r,c)), is(nullValue()));
                }
            }
        }
    }

    // Same test as alpha civ. Winning mechanism is the same for Delta Civ
    @Test
    public void verifyWinnerIsRedAtThreeThousandBC(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getWinner(), is(nullValue()));
        for(int i = 0; i < 10; i++)
            game.endOfTurn();
        assertThat(game.getAge(), is(-3000));
        assertThat(game.getWinner(), is(Player.RED));
    }
    // Same test as Alpha Civ. Aging mechanism is the same for DeltaCiv
    @Test
    public void ageAdvancesOneHundredYearsEveryTurn(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getAge(), is(-4000));
        for(int i = 0; i < 9; i++){
            game.endOfTurn();
            assertThat(game.getAge(), is(-4000 + ((i+1) * 100)));
        }
    }

    //This tests a proper Settler action according to the Delta Civ requirements
    //Nothing happens
    @Test
    public void testSettlerAction(){

        assertThat(game, is(notNullValue()));
        assertThat(game.getUnitAt(new Position(5,5)).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getCityAt(new Position(5,5)), is(nullValue()));
        game.performUnitActionAt(new Position(5,5));
        assertThat(game.getUnitAt(new Position(5,5)).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getCityAt(new Position(5,5)), is(nullValue()));
    }

    //This tests a proper Archer action according to the Delta Civ requirements
    //nothing happens
    @Test
    public void testArcherAction(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getUnitAt(new Position(3,8)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(3,8)).getDefensiveStrength(), is(3));
        game.performUnitActionAt(new Position(3,8));
        assertThat(game.getUnitAt(new Position(3,8)).getDefensiveStrength(), is(3));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.moveUnit(new Position(3,8),(new Position(3,9))), is(true));
        assertThat(game.getUnitAt(new Position(3,9)).getTypeString(), is(GameConstants.ARCHER));
        game.performUnitActionAt(new Position(3,9));
        assertThat(game.getUnitAt(new Position(3,9)).getDefensiveStrength(), is(3));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.moveUnit(new Position(3,9),(new Position(3,8))), is(true));
    }

}
