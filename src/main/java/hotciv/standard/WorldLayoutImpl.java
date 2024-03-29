package hotciv.standard;

import hotciv.framework.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WorldLayoutImpl implements WorldLayout {
    String civVariation;
    int round=0;

    //Hashmaps for tiles, units, cities, and arraylist of cities
    //World Layout is where our world, and all objects in the world, are stored
    //The instantiation of World Layout in GameImpl serves as the map object that will be passed to different game functions
    //The Maps are indexed using the Position object, with the object type being stored
    public Map<Position, Tile> tileMap = new HashMap<>();
    public Map<Position, Unit> unitMap = new HashMap<>();
    public Map<Position, City> cityMap = new HashMap<>();
    public Map<Player, Integer> winsMap = new HashMap<>();
   // public ArrayList<City> cityList = new ArrayList<>();

    public WorldLayoutImpl(String civVariationIN){
        civVariation = civVariationIN;
        initializeWins();
    }

    public String[] layout;
    private void initializeWins(){
        winsMap.put(Player.RED,0);
        winsMap.put(Player.BLUE,0);
        winsMap.put(Player.GREEN,0);
        winsMap.put(Player.YELLOW,0);
    }

    //Initializes the world layout
    public void implementWorldLayout() {
        if(civVariation.equals(GameConstants.DELTACIV)){ //DeltaCiv's Map Layout
            layout =
                    new String[] {
                            "...ooMooooo.....",
                            "..ohhoooofffoo..",
                            ".oooooMooo...oo.",
                            ".ooMMMoooo..oooo",
                            "...ofooohhoooo..",
                            ".ofoofooooohhoo.",
                            "...ooo..........",
                            ".ooooo.ooohooM..",
                            ".ooooo.oohooof..",
                            "offfoooo.offoooo",
                            "oooooooo...ooooo",
                            ".ooMMMoooo......",
                            "..ooooooffoooo..",
                            "....ooooooooo...",
                            "..ooohhoo.......",
                            ".....ooooooooo..",
                    };
            //DeltaCiv's City and Unite  layout
            cityMap.put(new Position(8,12), new CityImpl(Player.RED));
            cityMap.put(new Position(4, 5), new CityImpl(Player.BLUE));

            unitMap.put(new Position(3,8),new UnitImpl(Player.RED,GameConstants.ARCHER));
            unitMap.put(new Position(4,4),new UnitImpl(Player.BLUE,GameConstants.LEGION));
            unitMap.put(new Position(5,5),new UnitImpl(Player.RED,GameConstants.SETTLER));
            }

        else {
            //AlphaCiv's map layout
            layout =
                    new String[]{
                            "ohoooooooooooooo",
                            ".ooooooooooooooo",
                            "ooMooooooooooooo",
                            "oooooooooooooooo",
                            "oooooooooooooooo",
                            "oooooooooooooooo",
                            "oooooooooooooooo",
                            "oooooooooooooooo",
                            "oooooooooooooooo",
                            "oooooooooooooooo",
                            "oooooooooooooooo",
                            "oooooooooooooooo",
                            "oooooooooooooooo",
                            "oooooooooooooooo",
                            "oooooooooooooooo",
                            "oooooooooooooooo",
                    };
            //AlphaCiv's Unit and City layout
            cityMap.put(new Position(1, 1), new CityImpl(Player.RED));
            cityMap.put(new Position(4, 1), new CityImpl(Player.BLUE));
            unitMap.put(new Position(2, 0), new UnitImpl(Player.RED, GameConstants.ARCHER));
            unitMap.put(new Position(3, 2), new UnitImpl(Player.BLUE, GameConstants.LEGION));
            unitMap.put(new Position(4, 3), new UnitImpl(Player.RED, GameConstants.SETTLER));

            if(civVariation.equals(GameConstants.EPSILONCIV)){
                unitMap.put(new Position(4, 4), new UnitImpl(Player.BLUE, GameConstants.LEGION));
                unitMap.put(new Position(8, 8), new UnitImpl(Player.BLUE, GameConstants.LEGION));
                unitMap.put(new Position(8, 9), new UnitImpl(Player.RED, GameConstants.LEGION));
                unitMap.put(new Position(9, 9), new UnitImpl(Player.RED, GameConstants.LEGION));
                unitMap.put(new Position(9, 10), new UnitImpl(Player.RED, GameConstants.LEGION));
            }
        }
                // Basically we use a 'data driven' approach - code the
                // layout in a simple semi-visual representation, and
                // convert it to the actual Game representation.
                // Conversion...

        String line;
        for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
            line = layout[r];
            for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
                char tileChar = line.charAt(c);
                String type = "error";
                if ( tileChar == '.' ) { type = GameConstants.OCEANS; }
                if ( tileChar == 'o' ) { type = GameConstants.PLAINS; }
                if ( tileChar == 'M' ) { type = GameConstants.MOUNTAINS; }
                if ( tileChar == 'f' ) { type = GameConstants.FOREST; }
                if ( tileChar == 'h' ) { type = GameConstants.HILLS; }
                Position p = new Position(r,c);
                tileMap.put( p, new TileImpl(type));
            }
        }

    }

    //Mostly setters and getters for units/cities/tiles given a position
    public City getCityAt( Position p){ return cityMap.get(p); }
    public Unit getUnitAt( Position p ){ return  unitMap.get(p); }
    public Tile getTileAt( Position p ){ return tileMap.get(p); }

    public int[] getSurroundingTileCount( Position p){

        // This method will return an array of size 5
        // Each entry for the array will represent the quantity of each type of tile of the surrounding 8 tiles
        // The 5 entries will represent the count in the following order:
        // [0] - Plains, [1] - Oceans, [2] - Forests, [3] - Mountains, [4] - Hills

        int[] rows = {0,1,1,1,0,-1,-1,-1};
        int[] cols = {-1,-1,0,1,1,1,0,-1};

        int i = p.getRow();
        int j = p.getColumn();

        int[] tileCountArray = {0,0,0,0,0};


        for(int index = 0; index < rows.length; index++){

            String tileType = getTileAt(new Position(i + rows[index],j + cols[index])).getTypeString();

            switch (tileType){
                case GameConstants.PLAINS:
                    tileCountArray[0] += 1;
                    break;
                case GameConstants.OCEANS:
                    tileCountArray[1] += 1;
                    break;
                case GameConstants.FOREST:
                    tileCountArray[2] += 1;
                    break;
                case GameConstants.MOUNTAINS:
                    tileCountArray[3] += 1;
                    break;
                case GameConstants.HILLS:
                    tileCountArray[4] += 1;
                    break;
            }
        }
        return tileCountArray;
    }
    public void removeCityAt( Position p){ cityMap.remove(p); }
    public void removeUnitAt( Position p ){ unitMap.remove(p); }
    public void addCityAt( Position p, Player name){ cityMap.put(p,new CityImpl(name)); }
    public void addUnitAt( Position p, Player name, String unitType){ unitMap.put(p,new UnitImpl(name,unitType)); }
    public void addUnit( Position p, Unit unit){ unitMap.put(p,unit); }
    public void moveUnitTo(Position to, Position from){
        if(unitMap.get(from).getMoveCount()>0) //Makes sure unit has remaining moves left in turn
            unitMap.put(to,unitMap.get(from));
    }
    // Returns all the cities in play in a Collection type object
    public Collection<City> getCityList() {

        return cityMap.values();
    }
    public void addWin(Player player){
        int z = getWins(player);
        winsMap.put(player, z+1);
    }
    public int getWins(Player player){ return winsMap.get(player); }
    public void resetWins(){
        winsMap.put(Player.RED, 0);
        winsMap.put(Player.BLUE, 0);
        winsMap.put(Player.GREEN, 0);
        winsMap.put(Player.YELLOW, 0);
    }
    public int getRound(){
        return round;
    }
    public void incrementRound(){
        round++;
    }

    public void setTile(Position p, String t){tileMap.put( p, new TileImpl(t));}
}

