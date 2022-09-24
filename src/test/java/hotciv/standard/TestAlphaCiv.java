package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/** Skeleton class for AlphaCiv test cases
 Updated Oct 2015 for using Hamcrest matchers
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
public class TestAlphaCiv {
  private Game game;

  /** Fixture for alphaciv testing. */
  @Before
  public void setUp() {
    game = new GameImpl();
  }

  // FRS p. 455 states that 'Red is the first player to take a turn'.
  @Test
  public void shouldBeRedAsStartingPlayer() {
    assertThat(game, is(notNullValue()));
    assertThat(game.getPlayerInTurn(), is(Player.RED));
  }

  @Test
  public void verifyStartingTileLayout(){
    assertThat(game, is(notNullValue()));
    for(int c = 0; c < GameConstants.WORLDSIZE; c++){
      for(int r = 0; r < GameConstants.WORLDSIZE; r++){
        if(r == 1 & c == 0){
          assertThat(game.getTileAt(new Position(1,0)).getTypeString(), is(GameConstants.OCEANS));
        }else if(r == 0 & c == 1){
          assertThat(game.getTileAt(new Position(0,1)).getTypeString(), is(GameConstants.HILLS));
        }else if(r == 2 & c == 2){
          assertThat(game.getTileAt(new Position(2,2)).getTypeString(), is(GameConstants.MOUNTAINS));
        }else{
          assertThat(game.getTileAt(new Position(r,c)).getTypeString(), is(GameConstants.PLAINS));
        }
      }
    }
  }

  @Test
  public void verifyStartingUnitLayout(){
    assertThat(game, is(notNullValue()));
    for(int c = 0; c < GameConstants.WORLDSIZE; c++){
      for(int r = 0; r < GameConstants.WORLDSIZE; r++){
        if(r == 2 & c == 0){
          assertThat(game.getUnitAt(new Position(2,0)).getTypeString(), is(GameConstants.ARCHER));
          assertThat(game.getUnitAt(new Position(2,0)).getOwner(), is(Player.RED));
        }else if(r == 3 & c == 2){
          assertThat(game.getUnitAt(new Position(3,2)).getTypeString(), is(GameConstants.LEGION));
          assertThat(game.getUnitAt(new Position(3,2)).getOwner(), is(Player.BLUE));
        }else if(r == 4 & c == 3){
          assertThat(game.getUnitAt(new Position(4,3)).getTypeString(), is(GameConstants.SETTLER));
          assertThat(game.getUnitAt(new Position(4,3)).getOwner(), is(Player.RED));
        }else{
          assertThat(game.getUnitAt(new Position(r,c)), is(nullValue()));
        }
      }
    }
  }

  @Test
  public void verifyStartingTownLayout(){
    assertThat(game, is(notNullValue()));
    for(int c = 0; c < GameConstants.WORLDSIZE; c++){
      for(int r = 0; r < GameConstants.WORLDSIZE; r++) {
        if(r == 1 & c == 1){
          assertThat(game.getCityAt(new Position(1,1)).getOwner(), is(Player.RED));
        }else if(r == 4 & c == 1){
          assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.BLUE));
        }else{
          assertThat(game.getCityAt(new Position(r,c)), is(nullValue()));
        }
      }
    }
  }

  @Test
  public void verifyStartingAgeIsFourThousandBC(){
    assertThat(game, is(notNullValue()));
    assertThat(game.getAge(), is(-4000));
  }

  @Test
  public void ageAdvancesOneHundredYearsEveryTurn(){
    assertThat(game, is(notNullValue()));
    assertThat(game.getAge(), is(-4000));
    for(int i = 0; i < 9; i++){
      game.endOfTurn();
      assertThat(game.getAge(), is(-4000 + ((i+1) * 100)));
    }
  }
  @Test
  public void verifyWinner(){
    assertThat(game, is(notNullValue()));
    assertThat(game.getWinner(), is(nullValue()));
    for(int i = 0; i < 10; i++)
      game.endOfTurn();
    assertThat(game.getWinner(), is(Player.RED));
  }

  @Test
  public void verifyCitySize() {
    assertThat(game.getCityAt(new Position(1,1)).getSize(), is(1));
    assertThat(game.getCityAt(new Position(4,1)).getSize(), is(1));
  }

  @Test
  public void verifyTreasurySize() {
    assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(0));
    assertThat(game.getCityAt(new Position(4,1)).getTreasury(), is(0));
  }
  @Test
  public void verifyUnitCannotBeMovedByWrongPlayer(){
    assertThat(game, is(notNullValue()));
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    assertThat(game.moveUnit(new Position(3,2),new Position(4,2)),is(false));
  }

  @Test
  public void verifyUnitCannotMoveOnWrongTerrains(){
    assertThat(game, is(notNullValue()));
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    assertThat(game.moveUnit(new Position(2,0),new Position(1,0)),is(false));
  }

  @Test
  public void verifyUnitCannotMoveTooFar(){
    assertThat(game, is(notNullValue()));
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    assertThat(game.moveUnit(new Position(2,0),new Position(4,0)),is(false));
    assertThat(game.moveUnit(new Position(2,0),new Position(2,6)),is(false));
  }

  @Test
  public void verifySuccessfulMove(){
    assertThat(game, is(notNullValue()));
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    assertThat(game.moveUnit(new Position(2,0),new Position(2,1)),is(true));
    assertThat(game.getUnitAt(new Position(2,1)).getTypeString(), is(GameConstants.ARCHER));
    assertThat(game.getUnitAt(new Position(2,1)).getOwner(), is(Player.RED));
    assertThat(game.getUnitAt(new Position(2,0)), is(nullValue()));
  }
  @Test
  public void verifyRedSuccessfullyAttacksBlue(){
    assertThat(game, is(notNullValue()));
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    assertThat(game.moveUnit(new Position(4,3),new Position(3,2)),is(true));
    assertThat(game.getUnitAt(new Position(3,2)).getTypeString(), is(GameConstants.SETTLER));
    assertThat(game.getUnitAt(new Position(3,2)).getOwner(), is(Player.RED));
    assertThat(game.getUnitAt(new Position(4,3)), is(nullValue()));
  }
  /** REMOVE ME. Not a test of HotCiv, just an example of what
   matchers the hamcrest library has...
   @Test
   public void shouldDefinetelyBeRemoved() {
   // Matching null and not null values
   // 'is' require an exact match
   String s = null;
   assertThat(s, is(nullValue()));
   s = "Ok";
   assertThat(s, is(notNullValue()));
   assertThat(s, is("Ok"));
   // If you only validate substrings, use containsString
   assertThat("This is a dummy test", containsString("dummy"));
   // Match contents of Lists
   List<String> l = new ArrayList<String>();
   l.add("Bimse");
   l.add("Bumse");
   // Note - ordering is ignored when matching using hasItems
   assertThat(l, hasItems(new String[] {"Bumse","Bimse"}));
   // Matchers may be combined, like is-not
   assertThat(l.get(0), is(not("Bumse")));
   }*/
}