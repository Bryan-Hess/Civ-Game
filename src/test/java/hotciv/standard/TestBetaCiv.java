package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestBetaCiv {

    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl();
        ((GameImpl)game).setWorldAgingVariation(GameConstants.BETACIV);
        ((GameImpl)game).setDecideWinnerVariation(GameConstants.BETACIV);
    }

    @Test
    public void placeHolderTest(){
        assertThat(game, is(notNullValue()));
    }
}
