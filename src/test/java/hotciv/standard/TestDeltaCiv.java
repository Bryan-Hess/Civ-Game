package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestDeltaCiv {

    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl();
        ((GameImpl)game).setWorldLayoutVariation(GameConstants.DELTACIV);
    }

    @Test
    public void placeHolderTest(){
        assertThat(game, is(notNullValue()));
    }
}
