package hotciv.standard;

import hotciv.framework.*;

import java.util.HashMap;
import java.util.Map;

public class WorldLayoutImpl implements WorldLayout {

    String civVariation;



    public Map<Position, Tile> tileMap = new HashMap<>();
    public Map<Position, Unit> unitMap = new HashMap<>();
    public Map<Position, City> cityMap = new HashMap<>();
    public WorldLayoutImpl(String civVariationIN){
        civVariation = civVariationIN;
    }

    public String[] layout;
    public void implementWorldLayout() {

        if(civVariation.equals(GameConstants.DELTACIV)){
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
            cityMap.put(new Position(8,12), new CityImpl(Player.RED));
            cityMap.put(new Position(4, 5), new CityImpl(Player.BLUE));
            unitMap.put(new Position(2,0),new UnitImpl(Player.RED,GameConstants.ARCHER));
            unitMap.put(new Position(3,2),new UnitImpl(Player.BLUE,GameConstants.LEGION));
            unitMap.put(new Position(4,3),new UnitImpl(Player.RED,GameConstants.SETTLER));
            }

        else {
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
            cityMap.put(new Position(1, 1), new CityImpl(Player.RED));
            cityMap.put(new Position(4, 1), new CityImpl(Player.BLUE));
            unitMap.put(new Position(2, 0), new UnitImpl(Player.RED, GameConstants.ARCHER));
            unitMap.put(new Position(3, 2), new UnitImpl(Player.BLUE, GameConstants.LEGION));
            unitMap.put(new Position(4, 3), new UnitImpl(Player.RED, GameConstants.SETTLER));
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

    public City getCityAt( Position p){

        return cityMap.get(p);
    };

    public Unit getUnitAt( Position p ){

        return  unitMap.get(p);
    };

    public Tile getTileAt( Position p ){

        return tileMap.get(p);
    };

    public void removeCityAt( Position p){

        cityMap.remove(p);
    };

    public void removeUnitAt( Position p ){

        unitMap.remove(p);
    };

    public void addCityAt( Position p, Player name){

        cityMap.put(p,new CityImpl(name));
    };



    public void addUnitAt( Position p, Player name, String unitType){

        unitMap.put(p,new UnitImpl(name,unitType));
    };

    public void addUnit( Position p, Unit unit){

        unitMap.put(p,unit);


    }
    public void moveUnitTo(Position to, Position from){
        if(unitMap.get(to).getMoveCount()>0) //Makes sure unit has remaining moves left in turn
            unitMap.put(to,unitMap.get(from));
    }
}

