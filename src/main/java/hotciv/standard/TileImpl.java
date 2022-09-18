package hotciv.standard;

import hotciv.framework.Tile;

public class TileImpl implements Tile {
    public String tileType;
    public TileImpl(String type){
        tileType = type;
    }

    @Override
    public String getTypeString() {
        return tileType;
    }
}
