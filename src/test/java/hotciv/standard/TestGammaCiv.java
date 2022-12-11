package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.GameImpl;
import hotciv.standard.GammaCivFactory;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestGammaCiv {

    private Game game;

    private StubAttackDiceRoll stubAttackDiceRoll;
    private StubDefenseDiceRoll stubDefenseDiceRoll;

    @Before
    public void setUp() {
        stubAttackDiceRoll = new StubAttackDiceRoll();
        stubDefenseDiceRoll = new StubDefenseDiceRoll();
        game = new GameImpl(new GammaCivFactory());

    }
    //This tests a proper Settler action according to the Gamma Civ requirements
    //The settler turns into a city owned by the same player
    @Test
    public void testSettlerAction(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getUnitAt(new Position(4,3)).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getCityAt(new Position(4,3)), is(nullValue()));
        game.performUnitActionAt(new Position(4,3));
        assertThat(game.getUnitAt(new Position(4,3)), is(nullValue()));
        assertThat(game.getCityAt(new Position(4,3)).getOwner(), is(Player.RED));
    }

    //This tests a proper Archer action according to the Gamma Civ requirements
    //The archer loses its ability to move and doubles its defensive strength
    //These actions are revoked if action is called again
    @Test
    public void testArcherAction(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getUnitAt(new Position(2,0)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(2,0)).getDefensiveStrength(), is(3));
        game.performUnitActionAt(new Position(2,0));
        assertThat(game.getUnitAt(new Position(2,0)).getDefensiveStrength(), is(6));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.moveUnit(new Position(2,0),(new Position(3,0))), is(false));
        assertThat(game.getUnitAt(new Position(2,0)).getTypeString(), is(GameConstants.ARCHER));
        game.performUnitActionAt(new Position(2,0));
        assertThat(game.getUnitAt(new Position(2,0)).getDefensiveStrength(), is(3));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.moveUnit(new Position(2,0),(new Position(3,0))), is(true));
    }
    // Same test as alpha civ. Winning mechanism is the same for Gamma Civ
    @Test
    public void verifyWinnerIsRedAtThreeThousandBC(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getWinner(), is(nullValue()));
        for(int i = 0; i < 10; i++)
            game.endOfTurn();
        assertThat(game.getAge(), is(-3000));
        assertThat(game.getWinner(), is(Player.RED));
    }

    // Same test as Alpha Civ. Aging mechanism is the same for GammaCiv
    @Test
    public void ageAdvancesOneHundredYearsEveryTurn(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getAge(), is(-4000));
        for(int i = 0; i < 9; i++){
            game.endOfTurn();
            assertThat(game.getAge(), is(-4000 + ((i+1) * 100)));
        }
    }

    // Same test as AlphaCiv. Confirm same starting layout for GammaCiv
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
}
