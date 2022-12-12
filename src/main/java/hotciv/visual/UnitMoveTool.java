package hotciv.visual;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.view.CivDrawing;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

import static hotciv.view.GfxConstants.getPositionFromXY;

public class UnitMoveTool extends NullTool {
    private final Game game;
    private final DrawingEditor editor;

    private Figure dragFigure;
    private int lastX;
    private int lastY;

    private Position from;

    public UnitMoveTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;
    }


    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        editor.drawing().lock();
        Figure figure = editor.drawing().findFigure(x, y);

        from = getPositionFromXY(x, y);
        if (game.getUnitAt(from) == null || figure == null)
            return;

        Unit unit = game.getUnitAt(from);

        if (unit.getOwner() == game.getPlayerInTurn()) {
            dragFigure = figure;
            lastX = x;
            lastY = y;
        }
    }

    @Override
    public void mouseDrag(MouseEvent e, int x, int y) {
        if (dragFigure != null){
            dragFigure.moveBy(x - lastX, y - lastY);
            lastX = x;
            lastY = y;
        }
    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y){
        editor.drawing().unlock();

        if (dragFigure != null) { //On successful drag of unit
            Position to = getPositionFromXY(x,y);

            if (game.moveUnit(from, to))
                System.out.print("from "+from+" to "+to);
            else
                ((CivDrawing) editor.drawing()).worldChangedAt(from);
        }

        dragFigure = null;
        from = null;
    }
}
