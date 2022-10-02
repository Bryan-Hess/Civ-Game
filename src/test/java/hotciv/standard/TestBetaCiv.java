package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestBetaCiv {

    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(GameConstants.BETACIV);
       // ((GameImpl)game).setWorldAgingVariation(GameConstants.BETACIV);
       // ((GameImpl)game).setDecideWinnerVariation(GameConstants.BETACIV);
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
}
