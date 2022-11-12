package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Player;
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
        for(int i = 0; i < 10; i++)
            game.endOfTurn();
        assertThat(game.getAge(), is(-3000));
    }

    @Test
    public void validateTranscriptionOfAge(){

    }

    @Test
    public void validateTranscriptionOfMoveUnit(){

    }

    @Test
    public void validateTranscriptionOfEndOfTurn(){

    }

    @Test
    public void validateTranscriptionOfChangeWorkForceFocusInCity(){

    }

    @Test
    public void validateTranscriptionOfChangeProductionInCity(){

    }

    @Test
    public void validateTranscriptionOfAddUnitGameLevel(){

    }

    @Test
    public void validateTranscriptionOfPerformUnitAction(){

    }
}
