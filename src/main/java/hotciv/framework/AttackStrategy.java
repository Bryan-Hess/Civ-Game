package hotciv.framework;

import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;

public interface AttackStrategy {
    public boolean attackUnit(Position from, Position to, WorldLayout worldLayout);
}
