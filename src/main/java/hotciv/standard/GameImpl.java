package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;
import java.util.Map;

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
  WorldAgingImpl WorldAging;
  DecideWinnerImpl decideWinner;
  ArcherActionImpl archerAction;
  SettlerActionImpl settlerAction;

  WorldLayoutImpl worldLayout;

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
  public void setWorldLayoutVariation(String civVar){
    worldLayout = new WorldLayoutImpl(civVar);
    worldLayout.implementWorldLayout();
  }

  public GameImpl( String civVar){

    setWorldLayoutVariation(civVar);
    //Sets the implementations based on the Civ variant
    setWorldAgingVariation(civVar);
    setDecideWinnerVariation(civVar);
    setArcherActionVariation(civVar);
    setSettlerActionVariation(civVar);
    //Game starts on Red player in year 4000BC
    currentPlayer = Player.RED;
    currentAge = -4000;
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
    int oldR,oldC,newR,newC;
    oldR = from.getRow();
    oldC = from.getColumn();
    newR = to.getRow();
    newC = to.getColumn();
    //If trying to move a unit not owned by the player
    if(worldLayout.getUnitAt(from).getOwner() != currentPlayer){
      return false;
    }
    //if trying to move onto an ocean or mountain tile
    if(worldLayout.getTileAt(to).getTypeString().equals(GameConstants.OCEANS) || worldLayout.getTileAt(to).getTypeString().equals(GameConstants.MOUNTAINS)){
      return false;
    }
    //If trying to move an invalid distance
    if(java.lang.Math.abs(newR-oldR)>1 || java.lang.Math.abs(newC-oldC)>1){
      return false;
    }

    if(worldLayout.getUnitAt(to)!=null&&worldLayout.getUnitAt(from).getMoveCount()>0){  //If unit can move and is on selected tile
      if(currentPlayer.equals(worldLayout.getUnitAt(to).getOwner())){
        return false; //if the player tries to move their unit on a tile that already has one of their units
      }
      else{
        //In future iterations, we will compare attacking/defending strength.
        //For this iteration, we do not need to compare stats, because the attacker always wins
        worldLayout.removeUnitAt(to);
        worldLayout.moveUnitTo(to,from);
       // unitMap.remove(from);
        worldLayout.removeUnitAt(from);
        worldLayout.getUnitAt(to).countMove();
        return true;
      }
    } else if (worldLayout.getCityAt(to)!=null&&worldLayout.getUnitAt(from).getMoveCount()>0) { //If unit moving onto a city
        if(!currentPlayer.equals(worldLayout.getCityAt(to).getOwner())) { //If unit moves to enemy city, take the city
            worldLayout.moveUnitTo(to, from);
            worldLayout.removeUnitAt(from);
            worldLayout.getUnitAt(to).countMove();

            //Removes the city and places a new one, if requirements change down the line will need to add setter/getter for city ownership to cityImpl
            worldLayout.removeCityAt(to);
            worldLayout.addCityAt(to, currentPlayer);
            return true;
        }else{ //Otherwise the city is friendly
            worldLayout.moveUnitTo(to, from);
            worldLayout.removeUnitAt(from);
            worldLayout.getUnitAt(to).countMove();
        }
    } else if(worldLayout.getUnitAt(from).getMoveCount()>0){

      worldLayout.moveUnitTo(to,from);
      worldLayout.removeUnitAt(from);
      worldLayout.getUnitAt(to).countMove();
      return true;
    }
    return false;

  }
  public void endOfTurn() {
    currentAge = WorldAging.incrementAge(currentAge);
    //Changes player each turn
    if(currentPlayer==Player.BLUE){
      currentPlayer = Player.RED;
    }
    else{
      currentPlayer = Player.BLUE;
    }
    //Resets move count for every unit
    for (int i=0;i<GameConstants.WORLDSIZE;i++)
    {
      for(int j=0;j<GameConstants.WORLDSIZE;j++){
        if(worldLayout.getUnitAt(new Position(i,j))!=null){
          worldLayout.getUnitAt(new Position(i,j)).resetMoveCount();
        }
      }
    }
    //Increments production for each city and produces any units if treasury is sufficient
    for (int i=0;i<GameConstants.WORLDSIZE;i++)
    {
      for(int j=0;j<GameConstants.WORLDSIZE;j++){
        if(worldLayout.getCityAt(new Position(i,j))!=null){
          worldLayout.getCityAt(new Position(i,j)).setTreasury(6);
        }
      }
    }

    //Checks production for all cities and produces the unit if treasury is sufficient
    for (int i=0;i<GameConstants.WORLDSIZE;i++)
    {
      for(int j=0;j<GameConstants.WORLDSIZE;j++){
        if(worldLayout.getCityAt(new Position(i,j))!=null){
          boolean newUnit = false;
          //Switch case to check if treasury > cost of unit
          switch(worldLayout.getCityAt(new Position(i,j)).getProduction()){
            case GameConstants.ARCHER:
              if(worldLayout.getCityAt(new Position(i,j)).getTreasury()>=10) {
                worldLayout.getCityAt(new Position(i,j)).setTreasury(-10);
                newUnit = true;
              }
              break;
            case GameConstants.LEGION:
              if(worldLayout.getCityAt(new Position(i,j)).getTreasury()>=15) {
                worldLayout.getCityAt(new Position(i,j)).setTreasury(-15);
                newUnit = true;
              }
              break;
            case GameConstants.SETTLER:
              if(worldLayout.getCityAt(new Position(i,j)).getTreasury()>=30) {
                worldLayout.getCityAt(new Position(i,j)).setTreasury(-30);
                newUnit = true;
              }
              break;
            case "":
              break;
          }
          //If treasury > cost of unit, remove cost from treasury and produce unit in the first available tile
          if(newUnit){
            //NOTE: There is definitely a cleaner way to do this by implementing an algorithm
            if(worldLayout.getUnitAt(new Position(i,j))==null)
              worldLayout.addUnit(new Position(i,j),new UnitImpl(worldLayout.getCityAt(new Position(i,j)).getOwner(),worldLayout.getCityAt(new Position(i,j)).getProduction()));
            else if(worldLayout.getUnitAt(new Position(i,j-1))==null)
                worldLayout.addUnit(new Position(i,j-1),new UnitImpl(worldLayout.getCityAt(new Position(i,j)).getOwner(),worldLayout.getCityAt(new Position(i,j)).getProduction()));
            else if(worldLayout.getUnitAt(new Position(i+1,j-1))==null)
                worldLayout.addUnit(new Position(i+1,j-1),new UnitImpl(worldLayout.getCityAt(new Position(i,j)).getOwner(),worldLayout.getCityAt(new Position(i,j)).getProduction()));
            else if(worldLayout.getUnitAt(new Position(i+1,j))==null)
                worldLayout.addUnit(new Position(i+1,j),new UnitImpl(worldLayout.getCityAt(new Position(i,j)).getOwner(),worldLayout.getCityAt(new Position(i,j)).getProduction()));
            else if(worldLayout.getUnitAt(new Position(i+1,j+1))==null)
                worldLayout.addUnit(new Position(i+1,j+1),new UnitImpl(worldLayout.getCityAt(new Position(i,j)).getOwner(),worldLayout.getCityAt(new Position(i,j)).getProduction()));
            else if(worldLayout.getUnitAt(new Position(i,j+1))==null)
                worldLayout.addUnit(new Position(i,j+1),new UnitImpl(worldLayout.getCityAt(new Position(i,j)).getOwner(),worldLayout.getCityAt(new Position(i,j)).getProduction()));
            else if(worldLayout.getUnitAt(new Position(i-1,j+1))==null)
                worldLayout.addUnit(new Position(i-1,j+1),new UnitImpl(worldLayout.getCityAt(new Position(i,j)).getOwner(),worldLayout.getCityAt(new Position(i,j)).getProduction()));
            else if(worldLayout.getUnitAt(new Position(i-1,j))==null)
                worldLayout.addUnit(new Position(i-1,j),new UnitImpl(worldLayout.getCityAt(new Position(i,j)).getOwner(),worldLayout.getCityAt(new Position(i,j)).getProduction()));
            else if(worldLayout.getUnitAt(new Position(i-1,j-1))==null)
                worldLayout.addUnit(new Position(i-1,j-1),new UnitImpl(worldLayout.getCityAt(new Position(i,j)).getOwner(),worldLayout.getCityAt(new Position(i,j)).getProduction()));
          }
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

}

