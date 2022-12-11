package hotciv.visual;

import hotciv.framework.Game;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class CompositionTool extends NullTool {
    private enum State{MOVE, ENDTURN, FOCUS, ACTION}
    protected State state;
    private Game game;
    private DrawingEditor editor;

    //define tools
    protected ActionTool actionTool;
    protected EndTurnTool endTurnTool;
    protected UnitMoveTool unitMoveTool;
    protected SetFocusTool setFocusTool;

    public CompositionTool(DrawingEditor editor, Game game){
        this.game = game;
        this.editor = editor;

        actionTool = new ActionTool(editor, game);
        endTurnTool = new EndTurnTool(game);
        unitMoveTool = new UnitMoveTool(editor, game);
        setFocusTool = new SetFocusTool(editor, game);
    }

    public void mouseDown(MouseEvent e, int x, int y){
        super.mouseDown(e, x, y);
        this.setState(e, x, y);

        //call the tools, let the tools decide which action to perform
        if(state == State.ACTION) {
            actionTool.mouseDown(e, x, y);
        }else if(state == State.ENDTURN){
            endTurnTool.mouseDown(e, x, y);
        }else if(state == State.FOCUS){
            setFocusTool.mouseDown(e, x, y);
        }
        unitMoveTool.mouseDown(e, x, y);
    }

    public void mouseUp(MouseEvent e, int x, int y){
        super.mouseUp(e,x,y);
        unitMoveTool.mouseUp(e, x, y);
    }

    public void setState(MouseEvent e, int x, int y){
        //set the "State"
        if (x>=550 && x<=590 && y>=60 && y<=105) { //on end of turn shield
            state = State.ENDTURN;
        } else if (!e.isShiftDown() && game.getUnitAt(GfxConstants.getPositionFromXY(x,y)) != null || game.getCityAt(GfxConstants.getPositionFromXY(x,y)) != null){ //on a legit unit
            state = State.FOCUS;
        } else if (e.isShiftDown() && game.getUnitAt(GfxConstants.getPositionFromXY(x,y)) != null ){ // on a legit unit WITH shift pressed down
            state = State.ACTION;
        } else{
            state = null;
        }
    }
}
