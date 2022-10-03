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


    @Test
    public void testArcherAction(){
        //assertThat(game, is(notNullValue()));
        //assertThat(game.getUnitAt(new Position(2,0)).getTypeString(), is(GameConstants.ARCHER));
        //assertThat(game.getUnitAt(new Position(2,0)).getDefensiveStrength(), is(3));
        //game.performUnitActionAt(new Position(2,0));
        //assertThat(game.getUnitAt(new Position(2,0)).getDefensiveStrength(), is(6));
        game.moveUnit(new Position(2,0),(new Position(3,0)));
        //assertThat(game.getUnitAt(new Position(2,0)).getTypeString(), is(GameConstants.ARCHER));

    }
}
