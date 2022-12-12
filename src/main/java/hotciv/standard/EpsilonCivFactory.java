package hotciv.standard;

import hotciv.framework.*;

public class EpsilonCivFactory implements VariationFactory {
    private DiceRoll attackRoll;
    private DiceRoll defenseRoll;
    public EpsilonCivFactory(DiceRoll aR, DiceRoll dR){
        attackRoll = aR;
        defenseRoll = dR;
    }

    public DecideWinner createDecideWinnerStrategy(){ return new DecideWinnerImpl(GameConstants.EPSILONCIV); }
    public ArcherAction createArcherActionStrategy(){ return new ArcherActionImpl(GameConstants.ALPHACIV); }
    public SettlerAction createSettlerActionStrategy(){ return new SettlerActionImpl(GameConstants.ALPHACIV); }
    public WorldAging createWorldAgingStrategy(){ return new WorldAgingImpl(GameConstants.ALPHACIV); }
    public AttackStrategy createAttackStrategy(){
        return new AttackStrategyImpl(GameConstants.EPSILONCIV, attackRoll, defenseRoll);
    }
    public WorldLayout createWorldLayoutStrategy(){ return new WorldLayoutImpl(GameConstants.EPSILONCIV); }
    public UFOAction createUFOActionStrategy(){ return new UFOActionImpl(GameConstants.ALPHACIV); }

    public Production createProductionStrategy(){ return new ProductionImpl(GameConstants.ALPHACIV);}
}
