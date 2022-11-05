package hotciv.standard;

import hotciv.framework.*;

public class SemiCivFactory implements VariationFactory {

    private DiceRoll attackRoll;
    private DiceRoll defenseRoll;
    public SemiCivFactory(DiceRoll aR, DiceRoll dR){
        attackRoll = aR;
        defenseRoll = dR;
    }

    public WorldAging createWorldAgingStrategy(){ return new WorldAgingImpl(GameConstants.BETACIV); }

    public ArcherAction createArcherActionStrategy(){ return new ArcherActionImpl(GameConstants.GAMMACIV); }

    public SettlerAction createSettlerActionStrategy(){ return new SettlerActionImpl(GameConstants.GAMMACIV); }

    public AttackStrategy createAttackStrategy(){

        return new AttackStrategyImpl(GameConstants.EPSILONCIV, attackRoll, defenseRoll);
    }

    public DecideWinner createDecideWinnerStrategy(){ return new DecideWinnerImpl(GameConstants.EPSILONCIV); }

    public WorldLayout createWorldLayoutStrategy(){ return new WorldLayoutImpl(GameConstants.DELTACIV); }

}
