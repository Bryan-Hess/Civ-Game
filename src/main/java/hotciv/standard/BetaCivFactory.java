package hotciv.standard;

import hotciv.framework.*;

public class BetaCivFactory implements VariationFactory {

    public WorldAging createWorldAgingStrategy(){ return new WorldAgingImpl(GameConstants.BETACIV); }

    public ArcherAction createArcherActionStrategy(){ return new ArcherActionImpl(GameConstants.ALPHACIV); }

    public SettlerAction createSettlerActionStrategy(){ return new SettlerActionImpl(GameConstants.ALPHACIV); }

    public AttackStrategy createAttackStrategy(){
        DiceRoll attackRoll = new DiceRollImpl();
        DiceRoll defenseRoll = new DiceRollImpl();
        return new AttackStrategyImpl(GameConstants.ALPHACIV, attackRoll, defenseRoll);
    }

    public DecideWinner createDecideWinnerStrategy(){ return new DecideWinnerImpl(GameConstants.BETACIV); }

    public WorldLayout createWorldLayoutStrategy(){ return new WorldLayoutImpl(GameConstants.ALPHACIV); }

}