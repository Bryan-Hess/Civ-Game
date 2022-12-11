package stub;

import hotciv.standard.GameImpl;
import hotciv.stub.ObserverStub;
import hotciv.framework.*;

import hotciv.standard.AlphaCivFactory;
import hotciv.standard.GameImpl;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


public class TestObserverStub{
    private ObserverStub obs;
    private Game game;

    @Before
    public void setUp() {
        obs = new ObserverStub();
        game = new GameImpl(new AlphaCivFactory());
        game.addObserver(obs);
    }

    @Test
    public void worldChangedAt_ObserverTest_OnMoveUnit(){
        game.moveUnit(new Position(2,0), new Position(2,1));
    }

    @Test
    public void turnEnds_ObserverTest(){
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
    }

    @Test
    public void tileFocusChangedAt_ObserverTest(){
        game.setTileFocus(new Position(2,1));
    }

}