package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class TestThetaCiv {

    private Game game;
    private StubAttackDiceRoll stubAttackDiceRoll;
    private StubDefenseDiceRoll stubDefenseDiceRoll;

    @Before
    public void setUp() {
        stubAttackDiceRoll = new StubAttackDiceRoll();
        stubDefenseDiceRoll = new StubDefenseDiceRoll();
        game = new GameImpl(new ThetaCivFactory());
    }

    //Duplicate Tests from Gamma Civ to verify starting Functionality
    @Test
    public void testSettlerAction(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getUnitAt(new Position(4,3)).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getCityAt(new Position(4,3)), is(nullValue()));
        game.performUnitActionAt(new Position(4,3));
        assertThat(game.getUnitAt(new Position(4,3)), is(nullValue()));
        assertThat(game.getCityAt(new Position(4,3)).getOwner(), is(Player.RED));
    }

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

    @Test
    public void verifyWinnerIsRedAtThreeThousandBC(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getWinner(), is(nullValue()));
        for(int i = 0; i < 10; i++)
            game.endOfTurn();
        assertThat(game.getAge(), is(-3000));
        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void ageAdvancesOneHundredYearsEveryTurn(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getAge(), is(-4000));
        for(int i = 0; i < 9; i++){
            game.endOfTurn();
            assertThat(game.getAge(), is(-4000 + ((i+1) * 100)));
        }
    }

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

    //Tests to verify UFO functionality unique to ThetaCiv
    @Test
    public void verifyUFOCanCrossMountainsAndOceans(){

    }

    @Test
    public void verifyUFOCanBeProducedInCity(){

    }

    @Test
    public void verifyUFOCanFlyOverCityWithoutConquering(){

    }

    @Test
    public void verifyUFOAbductsOneCitizenFromEnemyCityWithPopulationAboveZero(){

    }

    @Test
    public void verifyUFORemovesCityIfAbductionActionDropCityToZeroPopulation(){

    }
    
}
