package hotciv.standard;

import hotciv.framework.*;


public class ProductionImpl implements Production {

    String civVariation;

    //private int tempPopulation;

    public ProductionImpl(String civVariationIN) {

        civVariation = civVariationIN;
    //    tempPopulation = 1;
    }

    public void incrementProduction(WorldLayout worldLayout) {

        // This class implements the production focus
        // If the variant is EtaCiv, then the population in the city is distributed to work on surrounding tiles that provide greater production
        // A city is required to have a population of at least 1, and will always have one worker on the city.
        // The city produces 1 production and 1 food
        // Forests provide 3 production
        // Hills provide 2 production
        // Mountains provide 1 production
        // Plains provide 3 food
        // Oceans provide 1 food

        int tempProduction;
        int tempFood;
        int[] surroundingTiles;
        int[] assignedTiles;
        int tempPopulation;

        if (civVariation.equals(GameConstants.ETACIV)) {
            for (int i=0;i<GameConstants.WORLDSIZE;i++){
                for(int j=0;j<GameConstants.WORLDSIZE;j++){
                    if(worldLayout.getCityAt(new Position(i,j))!=null){

                        // first get the size of the population
                        tempPopulation = worldLayout.getCityAt(new Position(i,j)).getSize();
                        // get the types of the 8 surrounding tiles.
                        // Comes in an array of size 5
                        // Each entry represents the count of each type of tile that surrounds the City
                        // The 5 entries will represent the count in the following order:
                        // [0] - Plains, [1] - Oceans, [2] - Forests, [3] - Mountains, [4] - Hills
                        surroundingTiles = worldLayout.getSurroundingTileCount(new Position(i,j));

                        // assign the workers based off the production focus
                        // the 1st worker automatically is assigned to the city, providing 1 prod and 1 food
                        // population can only be 1-9, so if the population is 1 then the loops will return all 0's
                        if(worldLayout.getCityAt(new Position(i,j)).getWorkforceFocus().equals(GameConstants.productionFocus))
                            assignedTiles = getProductionAssignedTiles(tempPopulation-1,surroundingTiles);
                        else
                            assignedTiles = getFoodAssignedTiles(tempPopulation-1,surroundingTiles);
                        //Get the production and food based off the values assigned for each tile
                        //add 1 each for the worker that works in the city
                        tempProduction = getTotalProduction(assignedTiles) + 1;
                        tempFood = getTotalFood(assignedTiles) + 1;

                        worldLayout.getCityAt(new Position(i,j)).setTreasury(tempProduction);
                        worldLayout.getCityAt(new Position(i,j)).setFood(tempFood);
                        if (worldLayout.getCityAt(new Position(i,j)).getFood() > (5+tempPopulation*3)){
                            if (tempPopulation<9){
                                worldLayout.getCityAt(new Position(i,j)).setSize(tempPopulation+1);
                                worldLayout.getCityAt(new Position(i,j)).resetFood();
                            }
                        }
                    }
                }
            }
        }
        else{
            // any other variant just increases the production by 6 for each city
            for (int i=0;i<GameConstants.WORLDSIZE;i++){
                for(int j=0;j<GameConstants.WORLDSIZE;j++){
                    if(worldLayout.getCityAt(new Position(i,j))!=null){
                        worldLayout.getCityAt(new Position(i,j)).setTreasury(6);
                    }
                }
            }
        }
    }

    private int getTotalProduction(int[] tiles){
        int prod = 0;
        //plains and oceans do not affect the production.
        //forests get 3 production, hills get 2, and mountains get 1
        prod += tiles[2]*3;
        prod += tiles[3];
        prod += tiles[4]*2;

        return prod;
    }

    private int getTotalFood(int[] tiles){
        int food = 0;
        //forests,hills, and mountains do not affect the food.
        //plains get 3 and oceans get 1
        food += tiles[0]*3;
        food += tiles[1];

        return food;
    }
    private int[] getProductionAssignedTiles(int population, int[] tiles){

        // The 5 entries will represent the count in the following order:
        // [0] - Plains, [1] - Oceans, [2] - Forests, [3] - Mountains, [4] - Hills
        // This method will go through each of the available workers and assign them to a tile
        // Priority goes in this order - Forests, Hills, Mountains,Plains,Oceans
        int[] assignedTiles = {0,0,0,0,0};
        for(int i=0;i<population;i++) {

            if (tiles[2] > 0) {
                assignedTiles[2] += 1;
                tiles[2] -= 1;
            } else if (tiles[4] > 0) {
                assignedTiles[4] += 1;
                tiles[4] -= 1;
            } else if (tiles[3] > 0) {
                assignedTiles[3] += 1;
                tiles[3] -= 1;
            } else if (tiles[0] > 0) {
                assignedTiles[0] += 1;
                tiles[0] -= 1;
            } else if (tiles[1] > 0) {
                assignedTiles[1] += 1;
                tiles[1] -= 1;
            }
        }
        return assignedTiles;

        }

    private int[] getFoodAssignedTiles(int population, int[] tiles){

        // The 5 entries will represent the count in the following order:
        // [0] - Plains, [1] - Oceans, [2] - Forests, [3] - Mountains, [4] - Hills
        // This method will go through each of the available workers and assign them to a tile
        // This is food focused
        // Priority goes in this order - Plains,Oceans, Forests, Hills, Mountains,
        int[] assignedTiles = {0,0,0,0,0};
        for(int i=0;i<population;i++) {

            if (tiles[0] > 0) {
                assignedTiles[0] += 1;
                tiles[0] -= 1;
            } else if (tiles[1] > 0) {
                assignedTiles[1] += 1;
                tiles[1] -= 1;
            } else if (tiles[2] > 0) {
                assignedTiles[2] += 1;
                tiles[2] -= 1;
            } else if (tiles[4] > 0) {
                assignedTiles[4] += 1;
                tiles[4] -= 1;
            } else if (tiles[3] > 0) {
                assignedTiles[3] += 1;
                tiles[3] -= 1;
            }
        }
        return assignedTiles;

    }
    }
