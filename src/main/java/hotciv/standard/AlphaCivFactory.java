package hotciv.standard;

import hotciv.framework.*;

public class AlphaCivFactory implements VariationFactory {
    public DecideWinner createDecideWinnerStrategy(){ return new DecideWinnerImpl(GameConstants.ALPHACIV); }
    public ArcherAction createArcherActionStrategy(){ return new ArcherActionImpl(GameConstants.ALPHACIV); }
    public SettlerAction createSettlerActionStrategy(){ return new SettlerActionImpl(GameConstants.ALPHACIV); }
    public WorldAging createWorldAgingStrategy(){ return new WorldAgingImpl(GameConstants.ALPHACIV); }
    public AttackStrategy createAttackStrategy(){
        DiceRoll attackRoll = new DiceRollImpl();
        DiceRoll defenseRoll = new DiceRollImpl();
        return new AttackStrategyImpl(GameConstants.ALPHACIV, attackRoll, defenseRoll);
    }
    public WorldLayout createWorldLayoutStrategy(){ return new WorldLayoutImpl(GameConstants.ALPHACIV); }
    public UFOAction createUFOActionStrategy(){ return new UFOActionImpl(GameConstants.ALPHACIV); } ;
}
