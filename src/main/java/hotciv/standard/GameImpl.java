package hotciv.standard;

import hotciv.framework.*;

import java.util.ArrayList;

/** Skeleton implementation of HotCiv.
 
   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Department of Computer Science
     Aarhus University
   
   Please visit http://www.baerbak.com/ for further information.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/

public class GameImpl implements Game {
    //Declaration of all game variables
    public Player currentPlayer;
    public int currentAge;
    //Declaration of implementations
    private WorldAging WorldAging;
    private DecideWinner decideWinner;
    private ArcherAction archerAction;
    private SettlerAction settlerAction;
    private UFOAction ufoAction;
    private AttackStrategy attackStrategy;
    private WorldLayout worldLayout;

    private Production productionStrategy;
    private VariationFactory factory;
    private ArrayList<String> transcript = new ArrayList<>();

    private GameObserver gameObserver;

    //Declares the implementations based on the Civ variant
    public void setWorldAgingVariation(String civVar){
    WorldAging = new WorldAgingImpl(civVar);
  }
    public void setDecideWinnerVariation(String civVar){
    decideWinner = new DecideWinnerImpl(civVar);
  }
    public void setArcherActionVariation(String civVar){
    archerAction = new ArcherActionImpl(civVar);
  }
    public void setSettlerActionVariation(String civVar){
    settlerAction = new SettlerActionImpl(civVar);
  }
    public void setAttackStrategyVariation(String civVar, DiceRoll attackRoll, DiceRoll defenseRoll){
        attackStrategy = new AttackStrategyImpl(civVar,attackRoll,defenseRoll);
    }
    public AttackStrategy getAttackStrategy() {
      return attackStrategy;
  }
    public void setWorldLayoutVariation(String civVar){
        worldLayout = new WorldLayoutImpl(civVar);
        worldLayout.implementWorldLayout();
    }

    public WorldLayout getWorldLayout() {
        return worldLayout;
    }

    public GameImpl( VariationFactory factory2){
        this.factory = factory2;
        this.worldLayout = factory.createWorldLayoutStrategy();
        this.worldLayout.implementWorldLayout();
        // setWorldLayoutVariation(civVar);
        //Sets the implementations based on the Civ variant
        this.settlerAction = factory.createSettlerActionStrategy();
        this.archerAction = factory.createArcherActionStrategy();
        this.ufoAction = factory.createUFOActionStrategy();
        this.attackStrategy = factory.createAttackStrategy();
        this.WorldAging = factory.createWorldAgingStrategy();
        this.decideWinner = factory.createDecideWinnerStrategy();
        this.productionStrategy = factory.createProductionStrategy();

        //Game starts on Red player in year 4000BC
        currentPlayer = Player.RED;
        currentAge = -4000;

        //Empty IMPL for when observer is not attached to avoid null returns
        gameObserver = new GameObserver() {
            @Override
            public void worldChangedAt(Position pos) {}
            @Override
            public void turnEnds(Player nextPlayer, int age) {}
            @Override
            public void tileFocusChangedAt(Position position) {}
        };
        this.addObserver(gameObserver);
    }

    //Getters for tiles/units/cities/player
    public Tile getTileAt( Position p ) {
    return worldLayout.getTileAt(p);
  }
    public Unit getUnitAt( Position p ) {
    return worldLayout.getUnitAt(p);
  }
    public City getCityAt( Position p ) {
    return worldLayout.getCityAt(p);
  }
    public Player getPlayerInTurn() {
    return currentPlayer;
  }
    public Player getWinner() {
        return decideWinner.getWinner(currentAge,worldLayout);
    }
    public int getAge() {
    return currentAge;
  }

  //Unit moving algorithm
    public boolean moveUnit( Position from, Position to ) {
        //If trying to move a unit not owned by the player

        if(worldLayout.getUnitAt(from).getOwner() != currentPlayer){
          return false;
        }
        //if trying to move onto an ocean or mountain tile
        boolean currentTileOceans = worldLayout.getTileAt(to).getTypeString().equals(GameConstants.OCEANS);
        boolean currentTileMountains = worldLayout.getTileAt(to).getTypeString().equals(GameConstants.MOUNTAINS);
        if( currentTileOceans || currentTileMountains ){
          if( getUnitAt(from).getPassThroughTerrain() ){
              worldLayout.moveUnitTo(to,from);
              worldLayout.removeUnitAt(from);
              worldLayout.getUnitAt(to).countMove();
              gameObserver.worldChangedAt(from);
              gameObserver.worldChangedAt(to);
              return true;
          }
          return false;
        }
        //If trying to move an invalid distance
        if(java.lang.Math.abs(to.getRow()-from.getRow())>1 || java.lang.Math.abs(to.getColumn()-from.getColumn())>1){
          return false;
        }

        //If unit has no valid moves
        if(0 >= worldLayout.getUnitAt(from).getMoveCount()){
            return false;
        }

        //If the target tile has a unit owned by the same player
        if(worldLayout.getUnitAt(to)!=null&&currentPlayer.equals(worldLayout.getUnitAt(to).getOwner())){
            return false; //if the player tries to move their unit on a tile that already has one of their units
        }

        //If the target tile has a unit owned by a different player (PVP COMBAT)
        if(worldLayout.getUnitAt(to)!=null){
            boolean retVal;
            retVal = pvpCombat(from, to);
            //Possibly need to check if retVal==true
            gameObserver.worldChangedAt(from);
            gameObserver.worldChangedAt(to);
            return retVal;
        }

        //If unit moving onto an enemy city with no enemy units
        if (worldLayout.getCityAt(to)!=null&&!currentPlayer.equals(worldLayout.getCityAt(to).getOwner())&&!worldLayout.getUnitAt(from).getTypeString().equals(GameConstants.UFO)){
            //Removes the city and places a new one, if requirements change down the line will need to add setter/getter for city ownership to cityImpl
            worldLayout.removeCityAt(to);
            worldLayout.addCityAt(to, currentPlayer);
        }

        //Default case, successfully move unit
        worldLayout.moveUnitTo(to,from);
        worldLayout.removeUnitAt(from);
        worldLayout.getUnitAt(to).countMove();
        gameObserver.worldChangedAt(from);
        gameObserver.worldChangedAt(to);
        return true;
    }
    public boolean pvpCombat( Position from, Position to ) {

        boolean retVale;
        retVale = attackStrategy.attackUnit(from, to, worldLayout);
        return retVale;
    }

    public void endOfTurn() {
        //Changes the age each turn
        currentAge = WorldAging.incrementAge(currentAge);
        //Changes player each turn
        currentPlayer = switchPlayerTurn(currentPlayer);
        //Resets move count for every unit
        resetAllUnitMoveCount();
        //Increments production for each city
        incrementAllCityProduction();

        //Checks production for all cities and produces the unit if treasury is sufficient
        for(int i = 0; i < GameConstants.WORLDSIZE; i++){
            for(int j = 0; j < GameConstants.WORLDSIZE; j++) {
                if(isTreasurySufficientForUnit(i,j)){
                    Player currentCityOwner = worldLayout.getCityAt(new Position(i,j)).getOwner();
                    String currentCityProduction = worldLayout.getCityAt(new Position(i,j)).getProduction();
                    Position emptyTile = findEmptyTileForCityUnitProduction(i,j);
                    worldLayout.addUnitAt(emptyTile,currentCityOwner,currentCityProduction);
                }
            }
        }
        worldLayout.incrementRound();
        gameObserver.turnEnds(getPlayerInTurn(), getAge());
      }
    public void changeWorkForceFocusInCityAt( Position p, String balance ) {
        if(worldLayout.getCityAt(p).getOwner().equals(currentPlayer)) {
            worldLayout.getCityAt(p).setWorkforceFocus(balance);
        }
    }

    public void changeProductionInCityAt( Position p, String unitType ) {
        //Sets city's production if a valid unit
        if(unitType.equals(GameConstants.ARCHER)|unitType.equals(GameConstants.LEGION)|unitType.equals(GameConstants.SETTLER)) {
        worldLayout.getCityAt(p).setProduction(unitType);
        } else if (unitType.equals(GameConstants.UFO)) {
            //Technically should have its own Impl file, will do this in later iteration
            worldLayout.getCityAt(p).setProduction(unitType);
        }
    }
    public void performUnitActionAt( Position p ) {
        String currUnitTypeString = worldLayout.getUnitAt(p).getTypeString();
        if(currUnitTypeString.equals(GameConstants.SETTLER)) {
            settlerAction.buildCity(p, worldLayout);
        }else if (currUnitTypeString.equals(GameConstants.ARCHER)) {
            archerAction.fortify(p, worldLayout);
        }else if (currUnitTypeString.equals(GameConstants.UFO)){
            ufoAction.abduct(p, worldLayout, currentPlayer);
        }
    
    }

    public Player switchPlayerTurn(Player p ){
      if(p == Player.BLUE){
          return Player.RED;
      }else{
          return Player.BLUE;
      }
    }

    public void resetAllUnitMoveCount(){
      for (int i=0;i<GameConstants.WORLDSIZE;i++) {
          for(int j=0;j<GameConstants.WORLDSIZE;j++){
              if(worldLayout.getUnitAt(new Position(i,j))!=null){
                  worldLayout.getUnitAt(new Position(i,j)).resetMoveCount();
                  worldLayout.getUnitAt(new Position(i,j)).resetActionCount();
              }
          }
      }
    }

    public void incrementAllCityProduction(){
        // call production function
        // break into its own impl file
      productionStrategy.incrementProduction(worldLayout);

    }

    public boolean isTreasurySufficientForUnit(int i, int j){
        City currentCity = worldLayout.getCityAt(new Position(i, j));
        if (currentCity != null) {
            //Switch case to check if treasury > cost of unit
            switch (currentCity.getProduction()) {
                case GameConstants.ARCHER:
                    if (currentCity.getTreasury() >= 10) {
                        currentCity.setTreasury(-10);
                        return true;
                    }
                    break;
                case GameConstants.LEGION:
                    if (currentCity.getTreasury() >= 15) {
                        currentCity.setTreasury(-15);
                        return true;
                    }
                    break;
                case GameConstants.SETTLER:
                    if (currentCity.getTreasury() >= 30) {
                        currentCity.setTreasury(-30);
                        return true;
                    }
                    break;
                case GameConstants.UFO:
                    if (currentCity.getTreasury() >= 60) {
                        currentCity.setTreasury(-60);
                        return true;
                    }
                    break;
                default:
                    return false;
            }
        }
        return false;
    }

    public Position findEmptyTileForCityUnitProduction(int i, int j){
        if(worldLayout.getUnitAt(new Position(i,j)) == null){
            return new Position(i,j);
        }
        int[] rows = {0,1,1,1,0,-1,-1,-1};
        int[] cols = {-1,-1,0,1,1,1,0,-1};
        for(int index = 0; index < rows.length; index++){
            boolean isEmptyTile = worldLayout.getUnitAt(new Position(i + rows[index],j + cols[index])) == null;
            if(isEmptyTile){
                return new Position(i + rows[index], j + cols[index]);
            }
        }
        return null;
    }

    public void addUnitGameLevel(Position p, Player name, String unitType){
        worldLayout.addUnitAt(p,name,unitType);
    }
    public void commitToTranscript(String s){
        transcript.add(s);
    }
    public ArrayList<String> getTranscript(){ return transcript; }

    public void setTileFocus(Position position) {
        if(getTileAt(position)!=null)
            getTileAt(position);
        else if(getCityAt(position)!=null)
            getCityAt(position);

        gameObserver.tileFocusChangedAt(position);
    }

    public void addObserver(GameObserver observer) {gameObserver = observer;}
}


