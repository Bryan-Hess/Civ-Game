package hotciv.visual;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class ActionTool extends NullTool {
    private Game game;
    private DrawingEditor editor;

    public ActionTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        super.mouseDown(e, x, y);
        if(e.isShiftDown() && game.getUnitAt(GfxConstants.getPositionFromXY(x,y)) != null ) {
            game.performUnitActionAt(GfxConstants.getPositionFromXY(e.getX(),e.getY()));
            editor.showStatus("Unit action: " + GfxConstants.getPositionFromXY(e.getX(),e.getY()));
        }
    }
}
