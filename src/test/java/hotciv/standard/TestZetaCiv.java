package hotciv.standard;

import hotciv.framework.*;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TestZetaCiv {

    private Game game;
    private StubAttackDiceRoll stubAttackDiceRoll;
    private StubDefenseDiceRoll stubDefenseDiceRoll;

    @Before
    public void setUp() {
        stubAttackDiceRoll = new StubAttackDiceRoll();
        stubDefenseDiceRoll = new StubDefenseDiceRoll();
        game = new GameImpl(new ZetaCivFactory());
    }

    @Test
    public void winnerIsOwnerOfAllCitiesBeforeRoundTwenty(){
        assertThat(game, is(notNullValue()));
        assertTrue(game.getAge() <= -2000);
        WorldLayout wl = game.getWorldLayout();
        wl.removeCityAt(new Position(4,1));
        wl.addCityAt(new Position(4,1), Player.RED);
        wl.removeCityAt(new Position(1,1));
        wl.addCityAt(new Position(1,1), Player.RED);
        System.out.println(wl.getCityList().size());
        boolean allCitiesConquered = false;
        if(game.getCityAt(new Position(1,1)).getOwner() == game.getCityAt(new Position(4,1)).getOwner()){
            allCitiesConquered = true;
        }
        assertTrue(allCitiesConquered);
        game.endOfTurn();
        assertThat(game.getWinner(), is(game.getCityAt(new Position(1,1)).getOwner()));
    }

    @Test
    public void winnerIsPlayerToWinThreeAttacksAfterRoundTwenty(){
        assertThat(game, is(notNullValue()));
        for(int i = 0; i < 21; i++){
            game.endOfTurn();
        }
        assertTrue(game.getAge() > -2000);
        boolean allCitiesNotConquered = false;
        if(game.getCityAt(new Position(1,1)).getOwner() != game.getCityAt(new Position(4,1)).getOwner()){
            allCitiesNotConquered = true;
        }
        assertTrue(allCitiesNotConquered);
        WorldLayout wl = game.getWorldLayout();
        for(int j = 0; j < 4; j++) {
            wl.addWin(Player.RED);
        }
        assertThat(game.getWinner(), is(Player.RED));
    }

}
