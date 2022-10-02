package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestDeltaCiv {

    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(GameConstants.DELTACIV);
        //((GameImpl)game).setWorldLayoutVariation(GameConstants.DELTACIV);
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
}
