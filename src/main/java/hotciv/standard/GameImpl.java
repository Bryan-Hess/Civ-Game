package hotciv.standard;

import hotciv.framework.*;

import java.net.SocketOption;

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

 // public Map<Position, Tile> tileMap;
 // public Map<Position, Unit> unitMap;
//  public Map<Position, City> cityMap;
  public Player currentPlayer;
  public int currentAge;

  WorldAgingImpl WorldAging;
  DecideWinnerImpl decideWinner;
  ArcherActionImpl archerAction;
  SettlerActionImpl settlerAction;

  WorldLayoutImpl worldLayout;

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
  public void setWorldLayoutVariation(String civVar){
    worldLayout = new WorldLayoutImpl(civVar);
    worldLayout.implementWorldLayout();
  }

  public GameImpl( String civVar){
    // Create a hashMap of all the tiles. A tile can be called by using its Position.
    // Each tile is type Plains as default, except for tiles (1,0),(0,1), and (2,2), which are Oceans, Hills, and Mountains, respectively.
    setWorldLayoutVariation(civVar);

    setWorldAgingVariation(civVar);
    setDecideWinnerVariation(civVar);
    setArcherActionVariation(civVar);
    setSettlerActionVariation(civVar);



    currentPlayer = Player.RED;
    currentAge = -4000;
  }

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
    if(currentAge >= -3000)
      return Player.RED;
    else
      return null;
  }
  public int getAge() {
    return currentAge;
  }
  public boolean moveUnit( Position from, Position to ) {
    //If trying to move a unit not owned by the player
    if(worldLayout.getUnitAt(from).getOwner() != currentPlayer){
      return false;
    }
    //if trying to move onto an ocean or mountain tile
    if(worldLayout.getTileAt(to).getTypeString().equals(GameConstants.OCEANS) || worldLayout.getTileAt(to).getTypeString().equals(GameConstants.MOUNTAINS)){
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

    //If the target tile has a unit owned by a differnt player (PVP COMBAT)
    if(worldLayout.getUnitAt(to)!=null){
        pvpCombat(from, to);
        return true;
    }

    //If unit moving onto an enemy city
    if (worldLayout.getCityAt(to)!=null&&!currentPlayer.equals(worldLayout.getCityAt(to).getOwner())) {
        //Removes the city and places a new one, if requirements change down the line will need to add setter/getter for city ownership to cityImpl
        worldLayout.removeCityAt(to);
        worldLayout.addCityAt(to, currentPlayer);
    }

    //Default case, successfully move unit
    worldLayout.moveUnitTo(to,from);
    worldLayout.removeUnitAt(from);
    worldLayout.getUnitAt(to).countMove();
    return true;
  }
  public void pvpCombat( Position from, Position to ) {
      //In future iterations, we will compare attacking/defending strength.
      //For this iteration, we do not need to compare stats, because the attacker always wins
      worldLayout.removeUnitAt(to);
      worldLayout.moveUnitTo(to,from);
      worldLayout.removeUnitAt(from);
      worldLayout.getUnitAt(to).countMove();
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
                  //Boolean isCurrentSpaceEmpty = worldLayout.getUnitAt((new Position(i,j))) == null;
                  Player currentCityOwner = worldLayout.getCityAt(new Position(i,j)).getOwner();
                  String currentCityProduction = worldLayout.getCityAt(new Position(i,j)).getProduction();
                  Position emptyTile = findEmptyTileForCityUnitProduction(i,j);
                  worldLayout.addUnitAt(emptyTile,currentCityOwner,currentCityProduction);

              }
          }
      }
  }

  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}

  public void changeProductionInCityAt( Position p, String unitType ) {
    //Sets city's production if a valid unit
    if(unitType.equals(GameConstants.ARCHER)|unitType.equals(GameConstants.LEGION)|unitType.equals(GameConstants.SETTLER)) {
      worldLayout.getCityAt(p).setProduction(unitType);
    }
  }
  public void performUnitActionAt( Position p ) {

    if(worldLayout.getUnitAt(p).getTypeString().equals(GameConstants.SETTLER)) {
        settlerAction.buildCity(p, worldLayout);
    } else if (worldLayout.getUnitAt(p).getTypeString().equals(GameConstants.ARCHER)) {
        archerAction.fortify(p, worldLayout);
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
              }
          }
      }
    }
    public void incrementAllCityProduction(){
      for (int i=0;i<GameConstants.WORLDSIZE;i++){
          for(int j=0;j<GameConstants.WORLDSIZE;j++){
              if(worldLayout.getCityAt(new Position(i,j))!=null){
                  worldLayout.getCityAt(new Position(i,j)).setTreasury(6);
              }
          }
      }
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

  }

