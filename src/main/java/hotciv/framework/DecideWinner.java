package hotciv.framework;

import hotciv.standard.CityImpl;
import java.util.List;

public interface DecideWinner {

    public Player getWinner(int currentAge, List<CityImpl> cities);
}
