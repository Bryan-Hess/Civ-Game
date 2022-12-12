package hotciv.visual;

import hotciv.framework.Game;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class EndTurnTool extends NullTool {
    private Game game;

    public EndTurnTool (Game game) {
        this.game = game;
    }

    public void mouseDown(MouseEvent e, int x, int y) {
        if (x>=550 && x<=590 && y>=60 && y<=105) //Ends turn on shield press
            game.endOfTurn();
    }
}
