package hotciv.framework;

public interface VariationFactory {

    public WorldAging createWorldAgingStrategy();
    public ArcherAction createArcherActionStrategy();
    public SettlerAction createSettlerActionStrategy();
    public AttackStrategy createAttackStrategy();
    public DecideWinner createDecideWinnerStrategy();
    public WorldLayout createWorldLayoutStrategy();

}
