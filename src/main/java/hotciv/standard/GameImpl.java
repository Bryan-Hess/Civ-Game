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

  public GameImpl() {
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
  public Player getWinner() { return null; }
  public int getAge() {
    return currentAge;
  }
  public boolean moveUnit( Position from, Position to ) {
    return false;
  }
  public void endOfTurn() {
    currentAge = currentAge + 100;
    if(currentPlayer==Player.BLUE){
      currentPlayer = Player.RED;
    }
    else{
      currentPlayer = Player.BLUE;
    }
  }
  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
  public void changeProductionInCityAt( Position p, String unitType ) {}
  public void performUnitActionAt( Position p ) {}
}
