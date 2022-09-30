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

  public Map<Position, Tile> tileMap;
  public Map<Position, Unit> unitMap;
  public Map<Position, City> cityMap;
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
  }

  public GameImpl(){
    // Create a hashMap of all the tiles. A tile can be called by using its Position.
    // Each tile is type Plains as default, except for tiles (1,0),(0,1), and (2,2), which are Oceans, Hills, and Mountains, respectively.

    tileMap = new HashMap<>();
    unitMap = new HashMap<>();
    cityMap = new HashMap<>();
    for(int i = 0; i<GameConstants.WORLDSIZE;i++){
      for(int j = 0; j<GameConstants.WORLDSIZE;j++){
        tileMap.put(new Position(i,j),new TileImpl(GameConstants.PLAINS));
      }
    }
    tileMap.put(new Position(1,0),new TileImpl(GameConstants.OCEANS));
    tileMap.put(new Position(0,1),new TileImpl(GameConstants.HILLS));
    tileMap.put(new Position(2,2),new TileImpl(GameConstants.MOUNTAINS));

    unitMap.put(new Position(2,0),new UnitImpl(Player.RED,GameConstants.ARCHER));
    unitMap.put(new Position(3,2),new UnitImpl(Player.BLUE,GameConstants.LEGION));
    unitMap.put(new Position(4,3),new UnitImpl(Player.RED,GameConstants.SETTLER));

    cityMap.put(new Position(1,1),new CityImpl(Player.RED));
    cityMap.put(new Position(4, 1), new CityImpl(Player.BLUE));

    currentPlayer = Player.RED;
    currentAge = -4000;
  }

  public Tile getTileAt( Position p ) {
    return tileMap.get(p);
  }
  public Unit getUnitAt( Position p ) {
    return unitMap.get(p);
  }
  public City getCityAt( Position p ) {
    return cityMap.get(p);
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
    int oldR,oldC,newR,newC;
    oldR = from.getRow();
    oldC = from.getColumn();
    newR = to.getRow();
    newC = to.getColumn();

    if(unitMap.get(from).getOwner() != currentPlayer){
      return false;
    }

    if(tileMap.get(to).getTypeString().equals(GameConstants.OCEANS) || tileMap.get(to).getTypeString().equals(GameConstants.MOUNTAINS)){
      return false;
    }

    if(java.lang.Math.abs(newR-oldR)>1 || java.lang.Math.abs(newC-oldC)>1){
      return false;
    }
    if(unitMap.get(to)!=null){
      if(currentPlayer==unitMap.get(to).getOwner()){
        return false; //if the player tries to move their unit on a tile that already has one of their units
      }
      else{
        //In future iterations, we will compare attacking/defending strength.
        //For this iteration, we do not need to compare stats, because the attacker always wins
        unitMap.remove(to);
        unitMap.put(to,unitMap.get(from));
        unitMap.remove(from);
        unitMap.get(to).countMove();
        return true;
      }
    }
    else{

      unitMap.put(to,unitMap.get(from));
      unitMap.remove(from);
      unitMap.get(to).countMove();
      return true;}

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
        if(unitMap.get(new Position(i,j))!=null){
          unitMap.get(new Position(i,j)).resetMoveCount();
        }
      }
    }
    //Increments production for each city and produces any units if treasury is sufficient
    for (int i=0;i<GameConstants.WORLDSIZE;i++)
    {
      for(int j=0;j<GameConstants.WORLDSIZE;j++){
        if(cityMap.get(new Position(i,j))!=null){
          cityMap.get(new Position(i,j)).setTreasury(6);
        }
      }
    }

    //Checks production for all cities and produces the unit if treasury is sufficient
    for (int i=0;i<GameConstants.WORLDSIZE;i++)
    {
      for(int j=0;j<GameConstants.WORLDSIZE;j++){
        if(cityMap.get(new Position(i,j))!=null){
          boolean newUnit = false;
          //Switch case to check if treasury > cost of unit
          switch(cityMap.get(new Position(i,j)).getProduction()){
            case GameConstants.ARCHER:
              if(cityMap.get(new Position(i,j)).getTreasury()>=10) {
                cityMap.get(new Position(i, j)).setTreasury(-10);
                newUnit = true;
              }
              break;
            case GameConstants.LEGION:
              if(cityMap.get(new Position(i,j)).getTreasury()>=15) {
                cityMap.get(new Position(i, j)).setTreasury(-15);
                newUnit = true;
              }
              break;
            case GameConstants.SETTLER:
              if(cityMap.get(new Position(i,j)).getTreasury()>=30) {
                cityMap.get(new Position(i, j)).setTreasury(-30);
                newUnit = true;
              }
              break;
            case "":
              break;
          }
          //If treasury > cost of unit, remove cost from treasury and produce unit in the first available tile
          if(newUnit){

            //NOTE: There is definitely a cleaner way to do this by implementing an algorithm

            if(unitMap.get(new Position(i,j))==null)
              unitMap.put(new Position(i,j),new UnitImpl(cityMap.get(new Position(i,j)).getOwner(),cityMap.get(new Position(i,j)).getProduction()));
            else if(unitMap.get(new Position(i,j-1))==null)
              unitMap.put(new Position(i,j-1),new UnitImpl(cityMap.get(new Position(i,j)).getOwner(),cityMap.get(new Position(i,j)).getProduction()));
            else if(unitMap.get(new Position(i+1,j-1))==null)
              unitMap.put(new Position(i+1,j-1),new UnitImpl(cityMap.get(new Position(i,j)).getOwner(),cityMap.get(new Position(i,j)).getProduction()));
            else if(unitMap.get(new Position(i+1,j))==null)
              unitMap.put(new Position(i+1,j),new UnitImpl(cityMap.get(new Position(i,j)).getOwner(),cityMap.get(new Position(i,j)).getProduction()));
            else if(unitMap.get(new Position(i+1,j+1))==null)
              unitMap.put(new Position(i+1,j+1),new UnitImpl(cityMap.get(new Position(i,j)).getOwner(),cityMap.get(new Position(i,j)).getProduction()));
            else if(unitMap.get(new Position(i,j+1))==null)
              unitMap.put(new Position(i,j+1),new UnitImpl(cityMap.get(new Position(i,j)).getOwner(),cityMap.get(new Position(i,j)).getProduction()));
            else if(unitMap.get(new Position(i-1,j+1))==null)
              unitMap.put(new Position(i-1,j+1),new UnitImpl(cityMap.get(new Position(i,j)).getOwner(),cityMap.get(new Position(i,j)).getProduction()));
            else if(unitMap.get(new Position(i-1,j))==null)
              unitMap.put(new Position(i-1,j),new UnitImpl(cityMap.get(new Position(i,j)).getOwner(),cityMap.get(new Position(i,j)).getProduction()));
            else if(unitMap.get(new Position(i-1,j-1))==null)
              unitMap.put(new Position(i-1,j-1),new UnitImpl(cityMap.get(new Position(i,j)).getOwner(),cityMap.get(new Position(i,j)).getProduction()));
          }
        }
      }
    }
  }
  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
  public void changeProductionInCityAt( Position p, String unitType ) {
    //Sets city's production if a valid unit
    if(unitType.equals(GameConstants.ARCHER)|unitType.equals(GameConstants.LEGION)|unitType.equals(GameConstants.SETTLER)) {
      cityMap.get(p).setProduction(unitType);
    }
  }
  public void performUnitActionAt( Position p ) {}
}
