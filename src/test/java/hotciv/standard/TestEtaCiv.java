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
public class TestEtaCiv {
    private Game game;

    private StubAttackDiceRoll stubAttackDiceRoll;
    private StubDefenseDiceRoll stubDefenseDiceRoll;

    /** Fixture for etaciv testing. */
    @Before
    public void setUp() {

        stubAttackDiceRoll = new StubAttackDiceRoll();
        stubDefenseDiceRoll = new StubDefenseDiceRoll();
        game = new GameImpl(new EtaCivFactory());
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
    public void verifyDefaultWorkFocus(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getCityAt(new Position(1,1)).getWorkforceFocus(), is(GameConstants.productionFocus));
        assertThat(game.getCityAt(new Position(4,1)).getWorkforceFocus(), is(GameConstants.productionFocus));
    }

    @Test
    public void verifyWorkFocusChange(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getCityAt(new Position(1,1)).getWorkforceFocus(), is(GameConstants.productionFocus));
        game.changeWorkForceFocusInCityAt(new Position(1,1),GameConstants.foodFocus);
        assertThat(game.getCityAt(new Position(1,1)).getWorkforceFocus(), is(GameConstants.foodFocus));
        assertThat(game.getCityAt(new Position(4,1)).getWorkforceFocus(), is(GameConstants.productionFocus));

    }

    @Test
    public void verifyPlayerCannotChangeWorkFocusForOtherPlayersCity(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getCityAt(new Position(1,1)).getWorkforceFocus(), is(GameConstants.productionFocus));
        game.changeWorkForceFocusInCityAt(new Position(4,1),GameConstants.foodFocus);
        assertThat(game.getCityAt(new Position(1,1)).getWorkforceFocus(), is(GameConstants.productionFocus));
        assertThat(game.getCityAt(new Position(4,1)).getWorkforceFocus(), is(GameConstants.productionFocus));

    }

    @Test
    public void verifyRedCityProducesProperOutputWithProductionFocusWithPopulation1(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(0));
        game.changeProductionInCityAt(new Position(1,1),GameConstants.ARCHER);
        game.endOfTurn();
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(1));

    }

    @Test
    public void verifyRedCityProducesProperOutputWithProductionFocusWithPopulation2(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(0));
        game.changeProductionInCityAt(new Position(1,1),GameConstants.ARCHER);
        game.getCityAt(new Position(1,1)).setSize(2);
        game.endOfTurn();
        //There is a hills tile next to this city. With only 1 additional worker, the production should be 1 + 2
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(1+2));

    }

    @Test
    public void verifyRedCityProducesProperOutputWithProductionFocusWithPopulation3(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(0));
        game.changeProductionInCityAt(new Position(1,1),GameConstants.ARCHER);
        game.getCityAt(new Position(1,1)).setSize(3);
        game.endOfTurn();
        //There is a hills tile and a mountain tile next to this city. With 2 additional workers, the production should be 1 + 2 + 1
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(1+2+1));
        assertThat(game.getCityAt(new Position(1,1)).getFood(), is(1));

    }

    @Test
    public void verifyRedCityProducesProperOutputWithProductionFocusWithPopulation6(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(0));
        assertThat(game.getCityAt(new Position(1,1)).getFood(), is(0));
        game.changeProductionInCityAt(new Position(1,1),GameConstants.ARCHER);
        game.getCityAt(new Position(1,1)).setSize(6);
        game.endOfTurn();
        //There is a hills tile and a mountain tile next to this city. With 5 additional workers, the production should be 1 + 2 + 1
        //The additional 3 workers will be assigned to the plains tiles next to the city
        //These workers will provide 3*3 food, but no production
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(1+2+1));
        assertThat(game.getCityAt(new Position(1,1)).getFood(), is(3*3 + 1));

    }

    @Test
    public void verifyRedCityProducesProperOutputWithFoodFocusWithPopulation1(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(0));
        assertThat(game.getCityAt(new Position(1,1)).getFood(), is(0));
        game.changeProductionInCityAt(new Position(1,1),GameConstants.ARCHER);
        game.changeWorkForceFocusInCityAt(new Position(1,1),GameConstants.foodFocus);
        game.endOfTurn();
        //with no additional workers, production and food will only increment 1
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(1));
        assertThat(game.getCityAt(new Position(1,1)).getFood(), is(1));

    }

    @Test
    public void verifyRedCityProducesProperOutputWithFoodFocusWithPopulation2(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(0));
        assertThat(game.getCityAt(new Position(1,1)).getFood(), is(0));
        game.changeProductionInCityAt(new Position(1,1),GameConstants.ARCHER);
        game.changeWorkForceFocusInCityAt(new Position(1,1),GameConstants.foodFocus);
        game.getCityAt(new Position(1,1)).setSize(2);
        game.endOfTurn();
        //There are 5 plains tiles next to this city. With only 1 additional worker,
        // the production should be 1, and the food should be 1 + 3
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(1));
        assertThat(game.getCityAt(new Position(1,1)).getFood(), is(1+3));

    }

    @Test
    public void verifyRedCityProducesProperOutputWithFoodFocusWithPopulation3(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(0));
        assertThat(game.getCityAt(new Position(1,1)).getFood(), is(0));
        game.changeProductionInCityAt(new Position(1,1),GameConstants.ARCHER);
        game.changeWorkForceFocusInCityAt(new Position(1,1),GameConstants.foodFocus);
        game.getCityAt(new Position(1,1)).setSize(3);
        game.endOfTurn();
        //There are 5 plains tiles next to this city. With 2 additional workers,
        // the production should be 1, and the food should be 1 + 3*2
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(1));
        assertThat(game.getCityAt(new Position(1,1)).getFood(), is(1+3*2));

    }

    @Test
    public void verifyRedCityProducesProperOutputWithFoodFocusWithPopulation7(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(0));
        assertThat(game.getCityAt(new Position(1,1)).getFood(), is(0));
        game.changeProductionInCityAt(new Position(1,1),GameConstants.ARCHER);
        game.changeWorkForceFocusInCityAt(new Position(1,1),GameConstants.foodFocus);
        game.getCityAt(new Position(1,1)).setSize(7);
        game.endOfTurn();
        // There are 5 plains tiles and 1 ocean tile adjacent to the city
        // the production should be 1
        // the food should be 1 + 3*5 + 1
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(1));
        assertThat(game.getCityAt(new Position(1,1)).getFood(), is(1+ 3*5 + 1));

    }

    @Test
    public void verifyCityIncreasesPopulation(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(0));
        assertThat(game.getCityAt(new Position(1,1)).getFood(), is(0));
        game.changeProductionInCityAt(new Position(1,1),GameConstants.ARCHER);
        game.changeWorkForceFocusInCityAt(new Position(1,1),GameConstants.foodFocus);
        game.getCityAt(new Position(1,1)).setSize(2);
        game.endOfTurn();
        // There are 5 plains tiles and 1 ocean tile adjacent to the city
        // the production should be 1
        // the food should be 1 + 3 = 4
        // The city needs >5 + 2*3 population to grow in size, so at least 12 food
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(1));
        assertThat(game.getCityAt(new Position(1,1)).getFood(), is(1+ 3));
        assertThat(game.getCityAt(new Position(1,1)).getSize(), is(2));
        game.endOfTurn();
        // now the food should be at 8, production 2, and population 2
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(1+1));
        assertThat(game.getCityAt(new Position(1,1)).getFood(), is(1+ 3 + 1 + 3));
        assertThat(game.getCityAt(new Position(1,1)).getSize(), is(2));
        game.endOfTurn();
        // now the food should get to 12
        // 12 > 11, so the population should increment to 3 and food resets to 0
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(1+1+1));
        assertThat(game.getCityAt(new Position(1,1)).getFood(), is(0));
        assertThat(game.getCityAt(new Position(1,1)).getSize(), is(3));

    }

    @Test
    public void verifyCityDoesNotExceedSize9(){
        assertThat(game, is(notNullValue()));
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(0));
        assertThat(game.getCityAt(new Position(1,1)).getFood(), is(0));
        game.changeProductionInCityAt(new Position(1,1),GameConstants.ARCHER);
        game.changeWorkForceFocusInCityAt(new Position(1,1),GameConstants.foodFocus);
        game.getCityAt(new Position(1,1)).setSize(9);
        game.endOfTurn();
        // There are 5 plains tiles and 1 ocean tile adjacent to the city
        // also 1 hills and 1 mountain tile
        // the production should be 1 + 2 + 1
        // the food should be 1 + 3*5 + 1 = 17
        // The city would need >5 + 9*3 population to grow in size, so at least 33 food
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is(1+2+1));
        assertThat(game.getCityAt(new Position(1,1)).getFood(), is(1+ 3*5 + 1));
        assertThat(game.getCityAt(new Position(1,1)).getSize(), is(9));
        game.endOfTurn();
        // food now will be 34
        // normally, the population would increase by 1 and the food would reset, as 34 > 5 + size*3 (32)
        // however, the city is at max population, so the food will remain and the population will remain stagnant
        assertThat(game.getCityAt(new Position(1,1)).getTreasury(), is((1+2+1)*2));
        assertThat(game.getCityAt(new Position(1,1)).getFood(), is((1+ 3*5 + 1)*2));
        assertThat(game.getCityAt(new Position(1,1)).getSize(), is(9));
    }
}