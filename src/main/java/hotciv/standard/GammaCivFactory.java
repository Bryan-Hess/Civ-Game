package hotciv.standard;

import hotciv.framework.*;

public class GammaCivFactory implements VariationFactory {

    public WorldAging createWorldAgingStrategy(){ return new WorldAgingImpl(GameConstants.ALPHACIV); }

    public ArcherAction createArcherActionStrategy(){ return new ArcherActionImpl(GameConstants.DELTACIV); }

    public SettlerAction createSettlerActionStrategy(){ return new SettlerActionImpl(GameConstants.DELTACIV); }

    public AttackStrategy createAttackStrategy(){
        DiceRoll attackRoll = new DiceRollImpl();
        DiceRoll defenseRoll = new DiceRollImpl();
        return new AttackStrategyImpl(GameConstants.ALPHACIV, attackRoll, defenseRoll);
    }

    public DecideWinner createDecideWinnerStrategy(){ return new DecideWinnerImpl(GameConstants.ALPHACIV); }

    public WorldLayout createWorldLayoutStrategy(){ return new WorldLayoutImpl(GameConstants.ALPHACIV); }

}
