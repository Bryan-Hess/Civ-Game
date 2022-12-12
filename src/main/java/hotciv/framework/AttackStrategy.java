package hotciv.framework;

public interface AttackStrategy {
    public boolean attackUnit(Position from, Position to, WorldLayout worldLayout);
}
