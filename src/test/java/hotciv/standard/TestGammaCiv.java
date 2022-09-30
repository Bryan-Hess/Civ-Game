package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestGammaCiv {

    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl();
        ((GameImpl)game).setSettlerActionVariation(GameConstants.GAMMACIV);
        ((GameImpl)game).setArcherActionVariation(GameConstants.GAMMACIV);
    }

    @Test
    public void placeHolderTest(){
        assertThat(game, is(notNullValue()));
    }
}
