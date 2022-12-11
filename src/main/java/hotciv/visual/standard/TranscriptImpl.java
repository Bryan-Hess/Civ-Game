package hotciv.visual.standard;

import hotciv.framework.*;

public class TranscriptImpl extends Transcript{

    public TranscriptImpl(GameImpl g, boolean t){
        super(g,t);
    }

    @Override
    public Player getWinner(){
        if(super.getTranscribing()){
            if(super.getWinner() != null){
                System.out.println(super.getWinner() + " has won!");
                super.getGame().commitToTranscript(super.getWinner() + " has won!\n");
            }
        }
        return super.getWinner();
    }

    @Override
    public int getAge(){
        if(super.getTranscribing()){
            System.out.println("Age:" + super.getAge()+".");
            super.getGame().commitToTranscript("Age:" + super.getAge()+".\n");
        }
        return super.getAge();
    }

    @Override
    public boolean moveUnit( Position from, Position to ){
        if(super.getTranscribing()){
            System.out.println(super.getPlayerInTurn() + " moves " + super.getUnitAt(from).getTypeString() +
                    " from " + from + " to " + to + ".");
            super.getGame().commitToTranscript(super.getPlayerInTurn() + " moves " + super.getUnitAt(from).getTypeString() +
                    " from " + from + " to " + to + ".\n");
        }
        return super.moveUnit(from,to);
    }

    @Override
    public void endOfTurn(){
        if(super.getTranscribing()){
            System.out.println(super.getPlayerInTurn() + " ends turn.");
            super.getGame().commitToTranscript(super.getPlayerInTurn() + " ends turn.\n");
        }
        super.endOfTurn();
    }

    @Override
    public void changeWorkForceFocusInCityAt( Position p, String balance ){
        if(super.getTranscribing()){
            System.out.println(super.getPlayerInTurn() + " changes work focus in city at " + p + " to " +
                    balance + ".");
            super.getGame().commitToTranscript(super.getPlayerInTurn() + " changes work focus in city at " + p + " to " +
                    balance + ".\n");
        }
        super.changeWorkForceFocusInCityAt(p,balance);
    }

    @Override
    public void changeProductionInCityAt( Position p, String unitType ){
        if(super.getTranscribing()){
            System.out.println(super.getPlayerInTurn() + " changes production in city at " + p + " to " +
                    unitType + ".");
            super.getGame().commitToTranscript(super.getPlayerInTurn() + " changes production in city at " + p + " to " +
                    unitType + ".\n");
        }
        super.changeProductionInCityAt(p,unitType);
    }

    @Override
    public void addUnitGameLevel(Position p, Player name, String unitType){
        if(super.getTranscribing()){
            System.out.println(name + " has created " + unitType + " at " + p + ".");
            super.getGame().commitToTranscript(name + " has created " + unitType + " at " + p + ".\n");
        }
        super.addUnitGameLevel(p,name,unitType);
    }

    @Override
    public void performUnitActionAt( Position p ){
        if(super.getTranscribing()){
            switch(super.getUnitAt(p).getTypeString()){
                case GameConstants.ARCHER:
                    System.out.println(super.getPlayerInTurn() + "'s " + GameConstants.ARCHER +
                            " is fortifying at " + p + ".\n");
                    super.getGame().commitToTranscript(super.getPlayerInTurn() + "'s " + GameConstants.ARCHER +
                            " is fortifying at " + p + ".\n");
                    break;
                case GameConstants.SETTLER:
                    System.out.println(super.getPlayerInTurn() + "'s " + GameConstants.SETTLER +
                            " is building a city at " + p + ".\n");
                    super.getGame().commitToTranscript(super.getPlayerInTurn() + "'s " + GameConstants.SETTLER +
                            " is building a city at " + p + ".\n");
                    break;
                case GameConstants.UFO:
                    System.out.println(super.getPlayerInTurn() + "'s " + GameConstants.UFO +
                            " is abducting at " + p + ".\n");
                    super.getGame().commitToTranscript(super.getPlayerInTurn() + "'s " + GameConstants.UFO +
                            " is abducting at " + p + ".\n");
                    break;
                default:
                    break;
            }
        }
        super.performUnitActionAt(p);
    }

}
