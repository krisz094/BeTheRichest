package hu.uniobuda.nik.betherichest.Factories;

import hu.uniobuda.nik.betherichest.GameObjects.Game;

/**
 * Created by krisz on 2017. 04. 01..
 */
/*
public abstract class ConditionalProvider {
    public abstract Boolean Evaluate(Game currentGame);
}*/

public interface ConditionalProvider {
    public Boolean Evaluate(Game currentGame);
}

class RankOfIDNeeded implements ConditionalProvider {
    int rank;
    int id;
    public RankOfIDNeeded(int id, int rank) {
        this.rank = rank;
        this.id = id;
    }

    @Override
    public Boolean Evaluate(Game currentGame) {
        return currentGame.gameState.getInvestmentRankById(id) >= rank;
    }
}

class UpgradeWithIDUnlocked implements ConditionalProvider{

    public UpgradeWithIDUnlocked(int id) {
        this.id = id;
    }

    int id;

    @Override
    public Boolean Evaluate(Game currentGame) {
        return currentGame.gameState.getUpgradeBoughtById(id);
    }
}

