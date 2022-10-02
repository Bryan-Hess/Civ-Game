package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestGammaCiv {

    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(GameConstants.GAMMACIV);
       // ((GameImpl)game).setSettlerActionVariation(GameConstants.GAMMACIV);
       // ((GameImpl)game).setArcherActionVariation(GameConstants.GAMMACIV);
    }

    @Test
    public void testSettlerAction(){

        assertThat(game, is(notNullValue()));
        assertThat(game.getUnitAt(new Position(4,3)).getTypeString(), is(GameConstants.SETTLER));
        assertThat(game.getCityAt(new Position(4,3)), is(nullValue()));
        game.performUnitActionAt(new Position(4,3));
        assertThat(game.getUnitAt(new Position(4,3)), is(nullValue()));
        assertThat(game.getCityAt(new Position(4,3)).getOwner(), is(Player.RED));
    }
}
