package hotciv.visual;

import hotciv.framework.DiceRoll;
import hotciv.framework.Game;
import hotciv.standard.DiceRollImpl;
import hotciv.standard.GameImpl;
import hotciv.standard.SemiCivFactory;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

public class ShowSemiCivGUI {
    public static void main(String[] args) {
        DiceRoll attackRoll = new DiceRollImpl();
        DiceRoll defenseRoll = new DiceRollImpl();
        Game game = new GameImpl(new SemiCivFactory(attackRoll, defenseRoll));

        DrawingEditor editor =
                new MiniDrawApplication( "Click and/or drag any item to see all game actions",
                        new HotCivFactory4(game) );
        editor.open();
        editor.showStatus("Click and drag any item to see Game's proper response.");
        editor.setTool( new CompositionTool(editor, game) );
    }
}
