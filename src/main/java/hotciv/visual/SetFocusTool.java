package hotciv.visual;

import hotciv.framework.Game;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class SetFocusTool extends NullTool {
    private Game game;
    private DrawingEditor editor;
    public SetFocusTool(DrawingEditor editor, Game game){
        this.editor = editor;
        this.game = game;
    }

    public void mouseDown(MouseEvent e, int x, int y){
        if (game.getUnitAt(GfxConstants.getPositionFromXY(x,y))!=null){
            game.setTileFocus(GfxConstants.getPositionFromXY(x,y));
            editor.showStatus(
                game.getUnitAt(GfxConstants.getPositionFromXY(x,y)).getOwner() + " " +
                    game.getUnitAt(GfxConstants.getPositionFromXY(x,y)).getTypeString() +
                    ". Defense: " +game.getUnitAt(GfxConstants.getPositionFromXY(x,y)).getDefensiveStrength() +
                    ". Attack: " +game.getUnitAt(GfxConstants.getPositionFromXY(x,y)).getAttackingStrength());
        }
        if (game.getCityAt(GfxConstants.getPositionFromXY(x,y))!=null){
            game.setTileFocus(GfxConstants.getPositionFromXY(x,y));
            editor.showStatus("Production " +
                game.getCityAt(GfxConstants.getPositionFromXY(x,y)).getProduction() +
                ". Treasury: " + game.getCityAt(GfxConstants.getPositionFromXY(x,y)).getTreasury() +
                ". Workforce Focus: " +game.getCityAt(GfxConstants.getPositionFromXY(x,y)).getWorkforceFocus());
        }
    }
}
