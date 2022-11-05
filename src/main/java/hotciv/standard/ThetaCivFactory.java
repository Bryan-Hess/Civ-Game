package hotciv.standard;

import hotciv.framework.*;

public class ThetaCivFactory implements VariationFactory {
    public DecideWinner createDecideWinnerStrategy(){ return new DecideWinnerImpl(GameConstants.ALPHACIV); }
    public ArcherAction createArcherActionStrategy(){ return new ArcherActionImpl(GameConstants.GAMMACIV); }
    public SettlerAction createSettlerActionStrategy(){ return new SettlerActionImpl(GameConstants.GAMMACIV); }
    public WorldAging createWorldAgingStrategy(){ return new WorldAgingImpl(GameConstants.ALPHACIV); }
    public AttackStrategy createAttackStrategy(){
        DiceRoll attackRoll = new DiceRollImpl();
        DiceRoll defenseRoll = new DiceRollImpl();
        return new AttackStrategyImpl(GameConstants.ALPHACIV, attackRoll, defenseRoll);
    }
    public WorldLayout createWorldLayoutStrategy(){ return new WorldLayoutImpl(GameConstants.ALPHACIV); }

}
