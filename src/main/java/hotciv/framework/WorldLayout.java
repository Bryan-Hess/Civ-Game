package hotciv.framework;

import java.util.Collection;

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

    public Collection<City> getCityList();


    public void addWin(Player owner);

    public int getWins(Player owner);

    public void resetWins();

    public int getRound();

    public void incrimentRound();
}
