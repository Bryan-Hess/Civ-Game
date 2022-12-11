package hotciv.visual.standard;

import hotciv.framework.*;

import java.util.ArrayList;

public class Transcript implements Game {
    private Game game;
    public boolean transcribing;

    public Transcript(Game g, boolean t) {
        game = g;
        transcribing = t;
    }

    public Tile getTileAt(Position p ){ return game.getTileAt(p); }
    public Unit getUnitAt(Position p ){ return game.getUnitAt(p); }
    public City getCityAt(Position p ){ return game.getCityAt(p); }
    public Player getPlayerInTurn(){ return game.getPlayerInTurn(); }
    public Player getWinner(){ return game.getWinner(); }
    public int getAge(){ return game.getAge(); }
    public boolean moveUnit( Position from, Position to ){
        return game.moveUnit(from,to);
    }
    public void endOfTurn(){ game.endOfTurn(); }
    public void changeWorkForceFocusInCityAt( Position p, String balance ){
        game.changeWorkForceFocusInCityAt(p,balance);
    }
    public void changeProductionInCityAt( Position p, String unitType ){
        game.changeProductionInCityAt(p,unitType);
    }
    public void addUnitGameLevel(Position p, Player name, String unitType){
        game.addUnitGameLevel(p,name,unitType);
    }
    public void performUnitActionAt( Position p ){ game.performUnitActionAt(p); }
    public Object getAttackStrategy(){ return game.getAttackStrategy(); }
    public WorldLayout getWorldLayout(){ return game.getWorldLayout(); }
    public void setTranscribing(boolean t){ transcribing = t; }
    public boolean getTranscribing(){ return transcribing; }
    public Game getGame(){ return game; }
    public void commitToTranscript( String s ){ game.commitToTranscript(s); }
    public ArrayList<String> getTranscript(){ return game.getTranscript(); }
    public void setTileFocus(Position position) { game.setTileFocus(position); }
    public void addObserver(GameObserver observer) { game.addObserver(observer); }
}
