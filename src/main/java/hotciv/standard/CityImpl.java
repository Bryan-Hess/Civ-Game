package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.Player;

public class CityImpl implements City {
    //Declaration of private variables
    private Player owner;
    private int treasury;
    private int size;
    private String production;

    public CityImpl(Player name){
        owner = name;
        size = 1;
        treasury = 0;
        production = "";
    }
    @Override
    public Player getOwner() {
        return owner;
    }
    @Override
    public int getSize() {
        return size;
    }
    @Override
    public int getTreasury() {
        return treasury;
    }
    @Override
    public String getProduction() {
        return production;
    }
    @Override
    public String getWorkforceFocus() {
        return null;
    }
    @Override
    public void setProduction(String prod) {
        production=prod;
    }
    @Override
    public void setTreasury(int amount) {
        treasury+=amount;
    }
}
