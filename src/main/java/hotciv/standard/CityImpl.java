package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

public class CityImpl implements City {
    //Declaration of private variables
    private Player owner;
    private int treasury;

    private int food;
    private int populationSize;
    private String production;



    private String focus;



    public CityImpl(Player name){
        owner = name;
        populationSize = 1;
        treasury = 0;
        food = 0;
        production = "";
        focus = GameConstants.productionFocus;
    }
    @Override
    public Player getOwner() {
        return owner;
    }
    @Override
    public int getSize() {
        return populationSize;
    }
    @Override
    public int getTreasury() {
        return treasury;
    }
    @Override
    public String getProduction() {
        return production;
    }

    public void setWorkforceFocus(String balance){ focus = balance;}
    @Override
    public String getWorkforceFocus() {
        return focus;
    }
    @Override
    public void setProduction(String prod) {
        production=prod;
    }
    @Override
    public void setTreasury(int amount) {
        treasury+=amount;
    }
    @Override
    public void setSize(int s) { populationSize=s; }

    public int getFood(){ return food;}

    public void setFood(int f){
        food += f;
    }

    public void resetFood(){ food = 0;}


}
