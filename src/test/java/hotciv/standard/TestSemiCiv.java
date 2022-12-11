package hotciv.standard;

import hotciv.framework.*;

import hotciv.visual.standard.GameImpl;
import hotciv.visual.standard.SemiCivFactory;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestSemiCiv {

    private Game game;
    private StubAttackDiceRoll stubAttackDiceRoll;
    private StubDefenseDiceRoll stubDefenseDiceRoll;

    @Before
    public void setUp() {
        stubAttackDiceRoll = new StubAttackDiceRoll();
        stubDefenseDiceRoll = new StubDefenseDiceRoll();

        game = new GameImpl(new SemiCivFactory(stubAttackDiceRoll,stubDefenseDiceRoll));

    }

    @Test
    public void shouldBeRedAsStartingPlayer() {
        assertThat(game, is(notNullValue()));
        assertThat(game.getPlayerInTurn(), is(Player.RED));
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

    //The settler turns into a city owned by the same player
    @Test
    public void testSettlerAction(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getUnitAt(new Position(5,5)).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getCityAt(new Position(5,5)), is(nullValue()));
        game.performUnitActionAt(new Position(5,5));
        assertThat(game.getUnitAt(new Position(5,5)), is(nullValue()));
        assertThat(game.getCityAt(new Position(5,5)).getOwner(), is(Player.RED));
    }

    //This tests a proper Archer action according to the Gamma Civ requirements
    //The archer loses its ability to move and doubles its defensive strength
    //These actions are revoked if action is called again
    @Test
    public void testArcherAction(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getUnitAt(new Position(3,8)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(3,8)).getDefensiveStrength(), is(3));
        game.performUnitActionAt(new Position(3,8));
        assertThat(game.getUnitAt(new Position(3,8)).getDefensiveStrength(), is(6));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.moveUnit(new Position(3,8),(new Position(3,9))), is(false));
        assertThat(game.getUnitAt(new Position(3,8)).getTypeString(), is(GameConstants.ARCHER));
        game.performUnitActionAt(new Position(3,8));
        assertThat(game.getUnitAt(new Position(3,8)).getDefensiveStrength(), is(3));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.moveUnit(new Position(3,8),(new Position(3,9))), is(true));
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
    public void win3toWin(){
        assertThat(game, is(notNullValue()));
        stubAttackDiceRoll.setRoll(6);
        stubDefenseDiceRoll.setRoll(1);

        //Win 1
        game.addUnitGameLevel((new Position(8,0)),Player.RED,GameConstants.LEGION);
        game.addUnitGameLevel((new Position(8,1)),Player.BLUE,GameConstants.SETTLER);
        assertThat(game.getUnitAt(new Position(8,0)).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(new Position(8,1)).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.moveUnit(new Position(8,0),new Position(8,1)),is(true));
        assertThat(game.getUnitAt(new Position(8,1)).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getWinner(), is(nullValue()));
        //Win 2
        game.addUnitGameLevel((new Position(8,2)),Player.BLUE,GameConstants.SETTLER);
        assertThat(game.getUnitAt(new Position(8,2)).getTypeString(), is(GameConstants.SETTLER));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.moveUnit(new Position(8,1),new Position(8,2)),is(true));
        assertThat(game.getUnitAt(new Position(8,2)).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getWinner(), is(nullValue()));
        //Win 3
        game.addUnitGameLevel((new Position(8,3)),Player.BLUE,GameConstants.SETTLER);
        assertThat(game.getUnitAt(new Position(8,3)).getTypeString(), is(GameConstants.SETTLER));
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.moveUnit(new Position(8,2),new Position(8,3)),is(true));
        assertThat(game.getUnitAt(new Position(8,3)).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getWinner(), is(Player.RED));
    }

    public void rollOf1ShouldLose(){
        stubAttackDiceRoll.setRoll(1);
        stubDefenseDiceRoll.setRoll(5);
        game.addUnitGameLevel((new Position(8,8)),Player.BLUE,GameConstants.LEGION);
        game.addUnitGameLevel((new Position(8,9)),Player.RED,GameConstants.LEGION);
        game.addUnitGameLevel((new Position(9,9)),Player.RED,GameConstants.LEGION);
        game.addUnitGameLevel((new Position(9,10)),Player.RED,GameConstants.LEGION);

        assertThat(game.moveUnit(new Position(9,9),new Position(8,8)),is(false));
        //Failed attacker is deleted
        assertThat(game.getUnitAt(new Position(9,9)),is(nullValue()));
    }
    @Test
    public void rollOf6ShouldWin(){
        stubAttackDiceRoll.setRoll(6);
        stubDefenseDiceRoll.setRoll(1);

        game.addUnitGameLevel((new Position(8,8)),Player.BLUE,GameConstants.LEGION);
        game.addUnitGameLevel((new Position(8,9)),Player.RED,GameConstants.LEGION);
        game.addUnitGameLevel((new Position(9,9)),Player.RED,GameConstants.LEGION);
        game.addUnitGameLevel((new Position(9,10)),Player.RED,GameConstants.LEGION);

        assertThat(game.moveUnit(new Position(9,9),new Position(8,8)),is(true));
        assertThat(game.getUnitAt(new Position(8,8)).getOwner(),is(Player.RED));
    }
}
