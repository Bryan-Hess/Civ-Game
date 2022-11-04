package hotciv.standard;

import hotciv.framework.*;

public class EpsilonCivFactory implements VariationFactory {

    public WorldAging createWorldAgingStrategy(){ return new WorldAgingImpl(GameConstants.ALPHACIV); }

    public ArcherAction createArcherActionStrategy(){ return new ArcherActionImpl(GameConstants.ALPHACIV); }

    public SettlerAction createSettlerActionStrategy(){ return new SettlerActionImpl(GameConstants.ALPHACIV); }

    public AttackStrategy createAttackStrategy(){
        DiceRoll attackRoll = new DiceRollImpl();
        DiceRoll defenseRoll = new DiceRollImpl();
        return new AttackStrategyImpl(GameConstants.EPSILONCIV, attackRoll, defenseRoll);
    }

    public DecideWinner createDecideWinnerStrategy(){ return new DecideWinnerImpl(GameConstants.EPSILONCIV); }

    public WorldLayout createWorldLayoutStrategy(){ return new WorldLayoutImpl(GameConstants.ALPHACIV); }

}
