package hotciv.framework;

import hotciv.standard.CityImpl;

public interface WorldLayout {

    public void implementWorldLayout();

    public City getCityAt( Position p);

    public Unit getUnitAt( Position p );

    public Tile getTileAt( Position p );

    public void removeCityAt( Position p);

    public void removeUnitAt( Position p );

    public void addCityAt( Position p, Player name);

    public void addUnitAt( Position p, Player name, String unitType);

    public void addUnit( Position p, Unit unit);

    public void moveUnitTo(Position to, Position from);

}
