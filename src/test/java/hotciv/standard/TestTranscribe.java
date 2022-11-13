package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestTranscribe {
    Game game;
    @Before
    public void setUp() {
        game = new TranscriptImpl(new GameImpl(new AlphaCivFactory()),true);
    }

    @Test
    public void validateTranscriptionOfWinner(){
        assertThat(game, is(notNullValue()));
        for(int i = 0; i < 10; i++) {
            game.endOfTurn();
        }
        assertThat(game.getAge(), is(-3000));
        assertThat(game.getTranscript().get(10), is("Age:-3000.\n"));
        assertThat(game.getWinner(), is(Player.RED));
        assertThat(game.getTranscript().get(11), is("RED has won!\n"));
    }

    @Test
    public void validateTranscriptionOfAge(){

    }

    @Test
    public void validateTranscriptionOfMoveUnit(){

        assertThat(game, is(notNullValue()));
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        assertThat(game.moveUnit(new Position(2,0),new Position(2,1)),is(true));
        assertThat(game.getUnitAt(new Position(2,1)).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(new Position(2,1)).getOwner(), is(Player.RED));
        assertThat(game.getUnitAt(new Position(2,0)), is(nullValue()));
        assertThat(game.getTranscript().get(0), is("RED moves archer from [2,0] to [2,1].\n"));

    }

    @Test
    public void validateTranscriptionOfEndOfTurn(){
        game.endOfTurn();
        assertThat(game.getTranscript().get(0),is("RED ends turn.\n"));
        game.endOfTurn();
        assertThat(game.getTranscript().get(1),is("BLUE ends turn.\n"));

    }

    @Test
    public void validateTranscriptionOfChangeWorkForceFocusInCity(){

    }

    @Test
    public void validateTranscriptionOfChangeProductionInCity(){

        assertThat(game, is(notNullValue()));
        game.changeProductionInCityAt(new Position(1,1),GameConstants.ARCHER);
        assertThat(game.getCityAt(new Position(1,1)).getProduction(), is(GameConstants.ARCHER));
        assertThat(game.getTranscript().get(0),is("RED changes production in city at [1,1] to archer.\n"));

    }

    @Test
    public void validateTranscriptionOfAddUnitGameLevel(){

        assertThat(game, is(notNullValue()));
        game.addUnitGameLevel((new Position(8,8)),Player.RED,GameConstants.ARCHER);
        game.endOfTurn();
        assertThat(game.getTranscript().get(0),is("RED has created archer at [8,8].\n"));
        assertThat(game.getTranscript().get(1),is("RED ends turn.\n"));
        game.addUnitGameLevel((new Position(9,9)),Player.BLUE,GameConstants.LEGION);
        game.endOfTurn();
        assertThat(game.getTranscript().get(2),is("BLUE has created legion at [9,9].\n"));
        assertThat(game.getTranscript().get(3),is("BLUE ends turn.\n"));

    }

    @Test
    public void validateTranscriptionOfPerformUnitAction(){
        assertThat(game, is(notNullValue()));
        game.performUnitActionAt(new Position(2,0));
        assertThat(game.getTranscript().get(0),is("RED's archer is fortifying at [2,0].\n"));
        game.endOfTurn();
        assertThat(game.getTranscript().get(1),is("RED ends turn.\n"));
        game.performUnitActionAt(new Position(4,3));
        assertThat(game.getTranscript().get(2),is("BLUE's settler is building a city at [4,3].\n"));
        game.endOfTurn();
        assertThat(game.getTranscript().get(3),is("BLUE ends turn.\n"));
    }
}
